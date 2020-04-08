<table border="0" cellspacing="0" cellpadding="0">
<tr>
  <td><img src='./bcnnm_git_logo.jpg' width=90></td><td><h2>BCNNM: a framework for <i>in silico</i> neural tissue development modeling </h1></td>
</tr>
</table>


Biological Cellular Neural Network Modeling (BCNNM) framework designed for building dynamic spatial models of neural tissue organization and basic stimulus dynamics. 

The experiment demonstrates a creation of an _in silico_ cerebral organoid-like structure, constituted of up to 1 million cells, which differentiate and self-organize into an interconnected system with four layers, where the spatial arrangement of layers and cells are consistent with the values of analogous parameters obtained from research on living tissues.

The BCNNM framework occupies a niche between highly detailed dynamic models of processes in individual cells or in small populations of cells and statistical models of very large cell populations, which, thanks to the simplified descriptions of low-level processes, make it possible to represent the collective behavior of tens of millions of cells.

## Requirements
- 16 GB RAM
- up to 50 GB of free space on disk to store results of simulation
- Linux/Mac
- Java 11+

## Running the model
There are 4 configurations for our platform. You can run following commands to run configuration you need:

`./gradlew Configuration_1A`

`./gradlew Configuration_1B`

`./gradlew Configuration_2A`

`./gradlew Configuration_2B`

