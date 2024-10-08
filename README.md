
# CPN_DAML_Paper Repository

This is the GitHub repository containing the source code and dependencies related to the paper titled “**Access Control Verification in Smart Contracts Using Colored Petri Nets**” by Issam Al-Azzoni and Saqib Iqbal submitted to the journal **MDPI Computers**.

There are three projects in the repository:

1. The Gradle project *cpntools\_project*:

This is a Java project that generates a CPN from a DAML model instance.

To execute the main class, enter the following command from inside the _cpntools_project_ folder:
```
gradle run --args="src\\main\\resources\\out_SimpleIou1.daml test_cpn4_1.cpn"
```

This will create the `test_cpn4_1.cpn` file in the _app_ folder. There are 8 test daml files in the _app/src/main/resources_ folder, including the `out_SimpleIou1.daml` file used in the above command.

2. The Gradle project _daml_project_:

This is a Java project that generates a DAML model instance from a DAML file.

To execute the main class, enter the following command from inside the _daml_project_ folder:
```
gradle run --args="src\\main\\resources\\SimpleIou.txt"
```

The generated DAML file will be stored in the _app/out_src/main/resources_ folder.

3. The Python project _python_:

This is a Python project that parses a state space graph generated by CPN Tools and lists all simple paths from the root node to the target nodes, as discussed in the paper.

To execute the Python program, run the following:
```
cd python

pipenv install

pipenv shell

python load_and_print.py
```
This will parse the state space graph stored in the file `StateSpace_cpn_1.dot`
