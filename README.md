# SimpleTracelinkDiscovery
Simple Tracelink Discovery (STD) is a tool for Traceability Link Recovery.

## Basic Functionality
STD calculates trace links between a software documentation and a model by matching the model entity names and its n-grams with words and n-grams in the documentation lines. 

For instance, for the entity ``AutomatedActionFactory``, the matches of  *"automatedactionfactory", "automated action", "action factory" and "automated action factory"* in each documentation line are counted. If the number of matches exceeds a certain threshold (match number threshold), a trace link between this entity and the corresponding documentation line is created. 

For string comparison to match n-grams or entity names, the Levenshtein Distance is used with a simple normalization to the range [0,1]. 
If this value is greater than or equal to a defined similarity threshold, a match is counted. A threshold of 1.0 means that both strings or n-grams must be identical. Cases are always ignored. 
Analogously, the Jaro-Winkler Similarity can be used instead of the normalized Levenshtein Distance. 

## Resulsts
We evaluated the traceability link recovery on 4 open source projects. 
The table below shows the results with a match count threshold of 1 and a similarity threshold of 0.9 for the normalized Levenshtein Distance. 

| Project | precision | recall | f1-score |
| - | - | - | - |
| Media Store | 1.0 | 0.62 | 0.77 |
| TeaStore | 0.94 | 0.57 | 0.71 |
| TEAMMATES | 0.89 | 0.57 | 0.70 |
| BigBlueButton | 0.38 | 0.17 | 0.24 |