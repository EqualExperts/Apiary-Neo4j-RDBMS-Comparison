##Apiary - Neo4j-RDBMS Comparison <a href="http://www.eelabs.co.uk/projects/tayra/"><img src="http://www.equalexperts.com/asset/images/EE-Labs-Logo-200x121px.jpg" height="90" width="120" align="right"></a>

###What is Apiary?
The application aims at graphically representing an organization that contains different groups/units of employees. 
Metaphorically, a group/unit can be viewed as a beehive and an employee as a bee and hence the whole organization 
becomes an apiary - hence the application name.  

Typical Usecases
1) Modeling a Simple Organization (Phase 1)
	Employees belong to single or multiple functional units lead by heads within an organization. An employee can have different types of reporting, direct reporting to his/her manager (can be conceptualized as a thick line relationship - in the figure below the direct lines coming out of a node are colored as grey) and multiple indirect reporting to different department heads/managers (can be conceptualized as a thin/dotted line relationship - in the figure below as green and red colored lines).
	For example, in a IT Services company or a captive center,  Adam may be reporting directly to Jack Ass, but for his project work, he would be functionally reporting to Jennifer and Harry as they are responsible for Cash Management and Corporate Banking.
	As the number of employees increase, so would the relationships and cross-functional reporting, eventually developing into a complex model that is non-hierarchical.

### De-Risking
Before jumping straight in to development of such an app, we asked the question that enterprise folks would ask - 
"what makes it a case for Neo4J? and can you prove it?" Basically de-risking and making a case for management buy in.  


### Build Info
We are using SBT 0.12.3 for our builds.  You can download it from [here](http://www.scala-sbt.org/release/docs/Getting-Started/Setup.html)
Please do not checkin Eclipse or Intellij or any IDE specific files.  For Idea or Eclipse they
can be generated using
* `sbt eclipse`
* `sbt gen-idea`
