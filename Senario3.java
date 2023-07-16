package cloudsimProject.cloudsim;
/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation
 *               of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009, The University of Melbourne, Australia
 */

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerSpaceShared;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;


/**
 * An example showing how to create
 * scalable simulations.
 */
public class Senario3 {
    
        
	/** The cloudlet list. */
	private static List<Cloudlet> cloudletList;

	/** The vmlist. */
	private static List<Vm> vmlist;
	

	private static List<Vm> createVM(int userId, int vms) {

		//Creates a container to store VMs. This list is passed to the broker later
		LinkedList<Vm> list = new LinkedList<Vm>();
		LinkedList<Vm> weightedlist = new LinkedList<Vm>();
		//VM Parameters
		long size = 10000; //image size (MB)
		int ram = 512; //vm memory (MB)
		long bw = 1000;
		int pesNumber = 1; //number of cpus
		String vmm = "Xen"; //VMM name
			
		//create VMs
		Vm[] vm = new Vm[vms];
		
		for(int i=0;i<vms;i++){
            int mips=(int)(Math.random()*4+1);
//            PeProvisionerSimple peProvisioner = new PeProvisionerSimple(1000);
//            BwProvisionerSimple bwProvisioner = new BwProvisionerSimple(2000);
			//vm[i] = new Vm(i, userId, 250*mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			vm[i] = new Vm(i, userId, 200*mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerSpaceShared());
			//vm[i] = Vm(i, userId, mips, pesNumber, ram, bw, size, priority, vmm, new CloudletSchedulerSpaceShared());
//			int thresholdRam = (int) (512 * 0.9);
//			RamProvisionerSimple ramProvisioner = new RamProvisionerSimple(thresholdRam);// Set the RAM utilization threshold to 90%
			list.add(vm[i]);
		}
		

		return list;
	}


	private static List<Cloudlet> createCloudlet(int userId, int cloudlets){
		// Creates a container to store Cloudlets
		LinkedList<Cloudlet> list = new LinkedList<Cloudlet>();

		//cloudlet parameters
		
		long fileSize = 300;
		long outputSize = 300;
		int pesNumber = 1;
		UtilizationModel utilizationModel = new UtilizationModelFull();

		Cloudlet[] cloudlet = new Cloudlet[cloudlets];
	

		for(int i=0;i<cloudlets;i++){
            long length = (long)(int )(Math.random() * 10 + 1);
            int pr=(int)(Math.random() * 4 + 1);
            System.out.println("cloudet "+ i + " pr "+ pr);
			cloudlet[i] = new Cloudlet(pr,i, length*1000, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel,true);
			// setting the owner of these Cloudlets
			cloudlet[i].setUserId(userId);
			list.add(cloudlet[i]);
		}

		return list;
	}



	////////////////////////// STATIC METHODS ///////////////////////

	/**
	 * Creates main() to run this example
	 */
	public static void main(String[] args) {
		Log.printLine("Starting Senario ...");

		try {
			// First step: Initialize the CloudSim package. It should be called
			// before creating any entities.
			int num_user = 1;   // number of grid users
			Calendar calendar = Calendar.getInstance();
			boolean trace_flag = false;  // mean trace events

			// Initialize the CloudSim library
			CloudSim.init(num_user, calendar, trace_flag);

			// Second step: Create Datacenters
			//Datacenters are the resource providers in CloudSim. We need at list one of them to run a CloudSim simulation
			@SuppressWarnings("unused")
			Datacenter datacenter0 = createDatacenter("Datacenter_0");
			@SuppressWarnings("unused")
			Datacenter datacenter1 = createDatacenter("Datacenter_1");

			//Third step: Create Broker
			DatacenterBroker broker = createBroker();
			int brokerId = broker.getId();
      
                        
			//Fourth step: Create VMs and Cloudlets and send them to broker
			vmlist = createVM(brokerId,4); //creating vms
			cloudletList = createCloudlet(brokerId,36); // creating cloudlets            
                        
//                        int random;
                        //assigning a random priority to tasks
//                        for(int i=0;i<36;i++){
//                        
//                        random=(int)(Math.random() * 4) + 1;
//                        cloudletList.get(i).setPr(random);
//                        }
			LinkedList<Cloudlet> list1 = new LinkedList<Cloudlet>();
			LinkedList<Cloudlet> list2 = new LinkedList<Cloudlet>();
			LinkedList<Cloudlet> list3 = new LinkedList<Cloudlet>();
			LinkedList<Cloudlet> list4 = new LinkedList<Cloudlet>();
			LinkedList<Cloudlet> listT = new LinkedList<Cloudlet>();
                        List<Integer> pr1=new ArrayList<Integer>();  
                        List<Integer> pr2=new ArrayList<Integer>(); 
                        List<Integer> pr3=new ArrayList<Integer>(); 
                        List<Integer> pr4=new ArrayList<Integer>();
                        List<Integer> prTotal=new ArrayList<Integer>(); 
                        for( int i=0; i<36; i++) {
                        	
                        	if (cloudletList.get(i).getPriority()== 1) {
                        		list1.add(cloudletList.get(i));
                        		System.out.println("@@PR1: " + i);
                        	}
                        	else if (cloudletList.get(i).getPr() == 2) {
                        		list2.add(cloudletList.get(i));
                        		
                        	}
                        	else if (cloudletList.get(i).getPr() == 3) {
                        		list3.add(cloudletList.get(i));
                        		
                        	}
                        	else {
                        		list4.add(cloudletList.get(i));
                        		
                        	}
                        }
                        
                        prTotal.addAll(pr1);
                        prTotal.addAll(pr2);
                        prTotal.addAll(pr3);
                        prTotal.addAll(pr4);
                        listT.addAll(list1);
                        listT.addAll(list2);
                        listT.addAll(list3);
                        listT.addAll(list4);
                        for (int i = 0 ; i < listT.size() ; i++) {
            				for (int j = i+1 ; j < listT.size(); j++) {
            					Cloudlet c = listT.get(i);
            					Cloudlet next = listT.get(j);
            					if (c.getCloudletLength() < next.getCloudletLength()) {
            						Cloudlet temp = c;
            						listT.set(i, next);
            						listT.set(j, temp);
            					}
            				}
            			}
                        
             
			broker.submitVmList(vmlist);
            broker.submitCloudletList(listT);
// weighted Round Robin Algoritm 
                                    
                           
           int[] vmWeights = {1, 2, 4, 6}; // Weights corresponding to VMs (1 is the weakest, 4 is the strongest)
                           
           for (int j = 0; j < listT.size(); j++) {
        	   int maxWeight = Arrays.stream(vmWeights).max().getAsInt();
               System.out.println("max weight: " + maxWeight );                             
               int maxIndex = 3;

               for (int i = 0; i < vmWeights.length; i++) {
            	   maxIndex = vmWeights[i] > vmWeights[maxIndex] ? i : maxIndex;
                   }
               System.out.println("max index: " + maxIndex );
                               
               broker.bindCloudletToVm(listT.get(j).getCloudletId(), maxIndex);
               vmWeights[maxIndex]--;
               if (vmWeights[maxIndex] == 0) {
            	   maxIndex = (maxIndex + 1) % 4;
               }
               if (vmWeights[0] == 0 & vmWeights[1] == 0 & vmWeights[2] == 0 & vmWeights[3] == 0  ) {
            	   vmWeights[0] = 1;
            	   vmWeights[1] = 2;
            	   vmWeights[2] = 3;
            	   vmWeights[3] = 4;
               }
               
             System.out.println("Now weights: " + vmWeights[maxIndex] );

             }
            

			// Fifth step: Starts the simulation
                        
			CloudSim.startSimulation();
			
			// Final step: Print results when simulation is over
			List<Cloudlet> newList = broker.getCloudletReceivedList();				 
			
			CloudSim.stopSimulation();
			
			printCloudletList(newList);

			Log.printLine("Senario 2 finished!");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.printLine("The simulation has been terminated due to an unexpected error");
		}
	}
	

	private static Datacenter createDatacenter(String name){

		// Here are the steps needed to create a PowerDatacenter:
		// 1. We need to create a list to store one or more
		//    Machines
		List<Host> hostList = new ArrayList<Host>();

		// 2. A Machine contains one or more PEs or CPUs/Cores. Therefore, should
		//    create a list to store these PEs before creating
		//    a Machine.
		List<Pe> peList1 = new ArrayList<Pe>();

		int mips = 1200;

		// 3. Create PEs and add these into the list.
		//for a quad-core machine, a list of 4 PEs is required:
		peList1.add(new Pe(0, new PeProvisionerSimple(mips * 0.9))); // need to store Pe id and MIPS Rating
		peList1.add(new Pe(1, new PeProvisionerSimple(mips * 0.9)));
		peList1.add(new Pe(2, new PeProvisionerSimple(mips * 0.9)));
		peList1.add(new Pe(3, new PeProvisionerSimple(mips * 0.9)));

		//Another list, for a dual-core machine
		List<Pe> peList2 = new ArrayList<Pe>();

		peList2.add(new Pe(0, new PeProvisionerSimple(mips * 0.9)));
		peList2.add(new Pe(1, new PeProvisionerSimple(mips * 0.9)));

		//4. Create Hosts with its id and list of PEs and add them to the list of machines
		int hostId=0;
		int ram = 2048; //host memory (MB)
		long storage = 1000000; //host storage
		int bw = 10000;
		int thresholdRam = (int) (ram * 0.9);


		hostList.add(
    			new Host(
    				hostId,
    				new RamProvisionerSimple(thresholdRam),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList1,
    				new VmSchedulerTimeShared(peList1)
    			)
    		); // This is our first machine

		hostId++;

		hostList.add(
    			new Host(
    				hostId,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList2,
    				new VmSchedulerTimeShared(peList2)
    			)
    		); // Second machine


		//To create a host with a space-shared allocation policy for PEs to VMs:
		//hostList.add(
    	//		new Host(
    	//			hostId,
    	//			new CpuProvisionerSimple(peList1),
    	//			new RamProvisionerSimple(ram),
    	//			new BwProvisionerSimple(bw),
    	//			storage,
    	//			new VmSchedulerSpaceShared(peList1)
    	//		)
    	//	);

		//To create a host with a oportunistic space-shared allocation policy for PEs to VMs:
		//hostList.add(
    	//		new Host(
    	//			hostId,
    	//			new CpuProvisionerSimple(peList1),
    	//			new RamProvisionerSimple(ram),
    	//			new BwProvisionerSimple(bw),
    	//			storage,
    	//			new VmSchedulerOportunisticSpaceShared(peList1)
    	//		)
    	//	);


		// 5. Create a DatacenterCharacteristics object that stores the
		//    properties of a data center: architecture, OS, list of
		//    Machines, allocation policy: time- or space-shared, time zone
		//    and its price (G$/Pe time unit).
		String arch = "x86";      // system architecture
		String os = "Linux";          // operating system
		String vmm = "Xen";
		double time_zone = 10.0;         // time zone this resource located
		double cost = 3.0;              // the cost of using processing in this resource
		double costPerMem = 0.05;		// the cost of using memory in this resource
		double costPerStorage = 0.1;	// the cost of using storage in this resource
		double costPerBw = 0.1;			// the cost of using bw in this resource
		LinkedList<Storage> storageList = new LinkedList<Storage>();	//we are not adding SAN devices by now

		DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
                arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage, costPerBw);


		// 6. Finally, we need to create a PowerDatacenter object.
		Datacenter datacenter = null;
		try {
			datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return datacenter;
	}

	//We strongly encourage users to develop their own broker policies, to submit vms and cloudlets according
	//to the specific rules of the simulated scenario
	private static DatacenterBroker createBroker(){

		DatacenterBroker broker = null;
		try {
			broker = new DatacenterBroker("Broker");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return broker;
	}
	

	/**
	 * Prints the Cloudlet objects
	 * @param list  list of Cloudlets
	 */
	private static void printCloudletList(List<Cloudlet> list) {
		int size = list.size();
		Cloudlet cloudlet;

		String indent = "    ";
		Log.printLine();
		Log.printLine("========== OUTPUT ==========");
		Log.printLine("Index" + indent + "Cloudlet ID" + indent + "STATUS" +
				 indent + "VM ID" + indent + "Length" + indent + "Time" + indent + "Start Time" + indent + "Finish Time");

		DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < size; i++) {
			cloudlet = list.get(i);
			Log.print(i + indent + indent + cloudlet.getCloudletId() + indent + indent + indent);

			if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){
				Log.print("SUCCESS");

				Log.printLine(indent + indent + cloudlet.getVmId() +
						indent + cloudlet.getCloudletLength() + indent + indent + dft.format(cloudlet.getActualCPUTime()) +
						indent + indent + dft.format(cloudlet.getExecStartTime())+ indent + indent + indent + dft.format(cloudlet.getFinishTime()));
			}
		}

	}
}
