# Weighted Round Robin Algorithm on Cloudsim
In this project, I implemented the weighted round robin algorithm in Cloudsim 3.0.3. In this project, cloudlet.java has been changed.
By default, this project includes 4 vms with different resources, each vm includes a weight for scheduling tasks.
First, for better performance, all cloudlets are sorted according to length, from high to low, and then larger tasks are assigned to VMs with more resources.
Of course, there is another project in this repository in which a priority is determined for each cloudlet and they are sorted according to priority and assigned to VMs.

## Simulator Used
- [Cloudsim 3.0.3](https://github.com/Cloudslab/cloudsim)

## How to Use
To use the code, you can delete the first cloudlet parameter in line 103 and run the code without changing the cloudlet.java file.   

**Remove (pr) like this:**  
cloudlet[i] = new Cloudlet(i, length*1000, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel,true);


Download the code and add it in the example package and run as usual. This code has been implemented during the initial time of my research to learn more about **clousim**.

