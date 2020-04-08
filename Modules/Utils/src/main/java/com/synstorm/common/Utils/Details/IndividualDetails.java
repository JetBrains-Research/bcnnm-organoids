package com.synstorm.common.Utils.Details;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.EnumTypes.CellFunctionalType;
import com.synstorm.common.Utils.EvolutionUtils.Gene.Gene;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;
import com.synstorm.common.Utils.TraceMessageWriter.PriorityTraceWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by human-research on 28/05/15.
 */

@Model_v0
@NonProductionLegacy

public class IndividualDetails implements IDetails {
    //region Fields
    private List<Gene> individualGenes;
    private Map<String, Integer> genePresentation;
    private Map<Integer, IndividualCellDetails> individualCellDetailsMap;
    private Set<Integer> cellsHashes;
    private Map<Integer, Set<Integer>> individualSynapses;
    private Map<String, Integer> actionsDurations;

    private UUID uuid;
    private UUID parent1UUID;
    private UUID parent2UUID;

    //endregion

    //region Constructors
    public IndividualDetails(UUID uuid, UUID parent1UUID, UUID parent2UUID) {
        this.uuid = uuid;
        this.parent1UUID = parent1UUID;
        this.parent2UUID = parent2UUID;
        this.individualGenes = new ArrayList<>();
        this.genePresentation = new HashMap<>();
        this.individualCellDetailsMap = new HashMap<>();
        this.individualSynapses = new TreeMap<>();
        this.cellsHashes = new HashSet<>();
        this.actionsDurations = new TreeMap<>();
    }

    //endregion

    //region Getters and Setters
    public UUID getUuid() {
        return uuid;
    }

    public UUID getParent1UUID() {
        return parent1UUID;
    }

    public UUID getParent2UUID() {
        return parent2UUID;
    }


    public int getNextAvailableCellID() {
        if (individualCellDetailsMap.size() == 0)
            return 0;
        else
            return Collections.max(individualCellDetailsMap.keySet()) + 1;
    }

    public List<Gene> getIndividualGenes() {
        return individualGenes;
    }

    public List<String> getIndividualGeneNames() {
        return individualGenes.stream()
                .map(Gene::getName)
                .collect(Collectors.toList());
    }

    public List<Double> getIndividualGeneDurations() {
        return individualGenes.stream()
                .map(Gene::getDuration)
                .collect(Collectors.toList());
    }

    public Map<String, Integer> getGenePresentation() {
        return genePresentation;
    }

    public IndividualCellDetails getCell(Integer cellNum) {
        return individualCellDetailsMap.get(cellNum);
    }

    public Map<Integer, IndividualCellDetails> getIndividualCellDetailsMap() {
        return individualCellDetailsMap;
    }

    public Set<Integer> getIndividualCellDetailsIndexes() {
        return individualCellDetailsMap.keySet();
    }

    public boolean vacantCoordinate(ICoordinate coordinate) {
        return cellsHashes.contains(coordinate.hashCode());
    }

    public Map<Integer, Set<Integer>> getIndividualSynapses() {
        return individualSynapses;
    }

    public Set<Integer> getNeuronSynapses(Integer cellNum) {
        return individualSynapses.get(cellNum);
    }

    public Gene getGene(String geneName) {
        Optional<Gene> optional = individualGenes.stream().filter(gene -> gene.getName().equals(geneName)).findAny();
        return optional.isPresent() ? optional.get() : null ;
    }
    //endregion

    //region Public Methods

    public boolean isActionsByGenesPositive() {
        return actionsDurations.entrySet().stream().noneMatch(e -> e.getValue() <= 0);
    }

    public IndividualDetails makeCopy(UUID childUUID) {
        return this.makeCopy(childUUID, this.getUuid(), this.getUuid());
    }

    public IndividualDetails makeCopy(UUID childUUID, UUID parent1UUID, UUID parent2UUID) {
        IndividualDetails result = new IndividualDetails(childUUID, parent1UUID, parent2UUID);

        List<Gene> tempIndividualGenes = new ArrayList<>();
        this.getIndividualGenes().stream().forEach(currentGene -> {
            Gene nGene = new Gene(currentGene.getName(), currentGene.getDuration(), currentGene.getMutability());
            currentGene.getActionPresentationMap().entrySet().stream()
                    .forEach(action -> nGene.addAction(action.getKey(), action.getValue()));
            tempIndividualGenes.add(nGene);
        });

        Map<String, Integer> tempGenePresentation = new HashMap<>();
        this.getGenePresentation().entrySet().stream()
                .forEach(entry -> tempGenePresentation.put(entry.getKey(), entry.getValue()));

        Set<Integer> tempCellHashes = new HashSet<>();
        tempCellHashes.addAll(this.cellsHashes);

        Map<Integer, IndividualCellDetails> tempIndividualCellDetailsMap = new HashMap<>();
        individualCellDetailsMap.entrySet().stream()
                .forEach(entry -> tempIndividualCellDetailsMap.put(entry.getKey(), entry.getValue().makeCopy()));

        Map<Integer, Set<Integer>> tempSynapses = new TreeMap<>();
        individualSynapses.entrySet().stream()
                .forEach(entry -> {
            Set<Integer> tempPostSynapseNeuronIDs = new TreeSet<>();
            tempPostSynapseNeuronIDs.addAll(entry.getValue());
            tempSynapses.put(entry.getKey(), tempPostSynapseNeuronIDs);
        });

        result.setIndividualCellDetailsMap(tempIndividualCellDetailsMap);
        result.setCellsHashes(tempCellHashes);
        result.setGenePresentation(tempGenePresentation);
        result.setIndividualGenes(tempIndividualGenes);
        result.setIndividualSynapses(tempSynapses);
        return result;
    }

    public IndividualDetails copyGenesInfo(IndividualDetails dest) {
        List<Gene> tempIndividualGenes = new ArrayList<>();
        Map<String, Integer> tempGenePresentation = new HashMap<>();
        for (int workingGene = 0; workingGene < this.getIndividualGenes().size(); workingGene++) {
            Gene currentGene = this.getIndividualGenes().get(workingGene);
            Gene nGene = new Gene(currentGene.getName(), currentGene.getDuration(), currentGene.getMutability());
            currentGene.getActionPresentationMap().forEach(nGene::addAction);
            tempIndividualGenes.add(nGene);
        }

        this.getGenePresentation().forEach(tempGenePresentation::put);

        dest.setIndividualGenes(tempIndividualGenes);
        dest.setGenePresentation(tempGenePresentation);
        return dest;
    }

    public void addGene(Gene geneToAdd) {
        String geneName = geneToAdd.getName();
        if (getGene(geneName) == null)
            individualGenes.add(geneToAdd);

        int check = checkGenePresentation(geneName);
        if (check == 0)
            genePresentation.put(geneName, 1);
        else
            genePresentation.put(geneName, ++check);
    }

    public void addCell(IndividualCellDetails individualCellDetails) {
        if (!cellsHashes.contains(individualCellDetails.getCoordinate().hashCode())) {
            if (!individualCellDetailsMap.containsKey(individualCellDetails.getId())) {
                individualCellDetailsMap.put(individualCellDetails.getId(), individualCellDetails);
                cellsHashes.add(individualCellDetails.getCoordinate().hashCode());
                if (!individualCellDetails.getCellFunctionalType().equals(CellFunctionalType.StemCell)) {
                   individualSynapses.put(individualCellDetails.getId(), new TreeSet<>());
                }
            } else
                PriorityTraceWriter.println("ALERT! Individual Cell Details Map contains key " + individualCellDetails.getId() + ". Nothing to do.", 0);

        } else
            PriorityTraceWriter.println("ALERT! Cell confict at coordinates: x=" + individualCellDetails.getCoordinate().getX()
                    + " y=" + individualCellDetails.getCoordinate().getY() + " z=" + individualCellDetails.getCoordinate().getZ()
                    + ". I've done nothing. Check your configuration", 0);
    }

    public void removeCell(Integer cellNumber) {
        IndividualCellDetails cellToRemove = individualCellDetailsMap.get(cellNumber);
        cellsHashes.remove(cellToRemove.getCoordinate().hashCode());
        individualCellDetailsMap.remove(cellNumber);
        individualSynapses.remove(cellNumber);
    }

    public void updateHash(int oldHash, int newHash) {
        cellsHashes.remove(oldHash);
        cellsHashes.add(newHash);
    }

    public void clearGenes() {
        genePresentation.clear();
        individualGenes.clear();
    }

    public void addSynapse(Integer from, Integer to) {
        if (!individualSynapses.containsKey(from))
            individualSynapses.put(from, new TreeSet<>());

        Set<Integer> currentCellSynapses = individualSynapses.get(from);
        currentCellSynapses.add(to);
    }

    public void removeSynapse(Integer from, Integer to) {
        Set<Integer> currentCellSynapses = individualSynapses.get(from);
        currentCellSynapses.remove(to);
    }

    public void addActionDuration(String action, Integer duration) {
        actionsDurations.put(action, duration);
    }

    public String getActionsHash() throws IOException, NoSuchAlgorithmException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (Integer duration : actionsDurations.values()) {
            byte[] bytes = ByteBuffer.allocate(4).putInt(duration).array();
            outputStream.write(bytes);
        }
        StringBuilder hexString = new StringBuilder();
        byte[] md5Hash = MessageDigest.getInstance("MD5").digest(outputStream.toByteArray());
        for (int i = 0; i < md5Hash.length; i++) {
            String hex = Integer.toHexString(0xFF & md5Hash[i]);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }

    //endregion


    //region Private Methods
    private void setGenePresentation(Map<String, Integer> genePresentation) {
        this.genePresentation = genePresentation;
    }

    private void setIndividualGenes(List<Gene> individualGenes) {
        this.individualGenes = individualGenes;
    }

    private void setIndividualCellDetailsMap(Map<Integer, IndividualCellDetails> individualCellDetailsMap) {
        this.individualCellDetailsMap = individualCellDetailsMap;
    }

    private void setCellsHashes(Set<Integer> cellsHashes) {
        this.cellsHashes = cellsHashes;
    }

    private void setIndividualSynapses(Map<Integer, Set<Integer>> individualSynapses) {
        this.individualSynapses = individualSynapses;
    }

    private int checkGenePresentation(String geneName) {
        int result = 0;
        if (genePresentation.containsKey(geneName))
            return genePresentation.get(geneName);

        return result;
    }
    //endregion


}
