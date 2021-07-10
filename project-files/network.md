# Network diagram
Issue ID|Description|Importance|Depends upon
:---:|:---|:---|:---:
22|Currency|must|
21|Basic settings for a file|must|22
23|Account|must|
31|Exchange rates|must|
24|Transaction|must|31
25|Choose an account|must|23
26|Create and view transactions|must|24
27|Choose a currency|must|22
36|Ask the user to overwrite existing file|must|18
30|Calculate average cost|must|23, 24
33|Calculate profits and losses|must|24, 30
20|Upgrade database in file|should|19
28|Different kinds of transactions|should|24
32|Retrieve exchange rates from external source|should|31
34|Export profits and losses to files of different formats|should|
37|About menu with help and license info|should|
29|Swedish translation|could|
35|If the user doesn't explicitly save a new file with an extension, add the standard automatically|could|
