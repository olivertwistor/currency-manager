# Network diagram
Issue ID|Description|Importance|Depends upon
:---:|:---|:---|:---:
18|Create a new file|must
19|Load a file|must
.|*Milestone 0.1.0*
22|Currency|must
21|Basic settings for a file|must|18, 22
23|Account|must
31|Exchange rates|must
24|Transaction|must|31
25|Choose an account|must|23
26|Create and view transactions|must|24
27|Choose a currency|must|22
.|*Milestone 0.2.0*
30|Calculate average cost|must|23, 24
33|Calculate profits and losses|must|24, 30
.|*Milestone 0.3.0*
20|Upgrade database in file|should|19
.|*Milestone 1.0.0*
28|Different kinds of transactions|should|24
.|*Milestone 1.1.0*
32|Retrieve exchange rates from external source|should|31
.|*Milestone 1.2.0*
34|Export profits and losses to files of different formats|33
.|*Milestone 1.3.0*
29|Swedish translation|could
.|*Milestone 1.4.0*
