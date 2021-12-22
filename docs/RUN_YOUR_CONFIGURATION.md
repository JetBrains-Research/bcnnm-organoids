**Run your configuration**

In order to run the configuration you wrote follow these steps:

1. Download the archive from the link [https://github.com/JetBrains-Research/bcnnm-organoids/raw/master/public/fncom.2020.588224.zip](https://github.com/JetBrains-Research/bcnnm-organoids/raw/master/public/fncom.2020.588224.zip).
2. Extract it.
3. Go to the directory `model/`.
4. Make sure that the file `SimulationModel-all.jar` and the directory `data` are present.
5. Create a subdirectory inside the `data`, for example, `my_configuration`.
6. Copy your configuration files `Model.xml`, `Individual.xml`, `Model.xsd`, `Individual.xsd` into the directory `my_configuration`. XSD schema examples can be found in `data/XSD/`.
7. Run the `SimulationModel-all.jar` using the following command line: `java -Xmx14g -Xms1g -jar SimulationModel-all.jar -processor SingleIndividualProcessor -configPath my_configuration -statisticExporters CellMovementExporter -indSeedNum 1`.
8. When the run is completed, the result will be stored in the `results` directory.
