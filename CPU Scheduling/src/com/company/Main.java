package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    // Driver Method
    public static void main(String[] args)
    {
        Scanner in =new Scanner(System.in);
        System.out.println("Number of processes: ");
        int numProcesses = in.nextInt();
        System.out.println("Quantum: ");
        int quantum = in.nextInt();
        System.out.println("Context: ");
        int context = in.nextInt();

        ArrayList<Process> processes = new ArrayList<>();
        for(int i=0;i<numProcesses;i++)
        {
            System.out.println("Process name :");
            String name = in.next();
            System.out.println("arrival burst priority");
            int a = in.nextInt();
            int b = in.nextInt();
            int p = in.nextInt();

            Process process = new Process(name , a , b,p);
            processes.add(process);
        }


        // Priority
        System.out.println("--------Priority Scheduling--------");
        Priority_Scheduling priority_scheduling = new Priority_Scheduling(context);
        for(Process p : processes)
            priority_scheduling.setProcess(p);

        System.out.println("Processes execution order ");
        priority_scheduling.Scheduling();
        priority_scheduling.waiting_time();
        System.out.println("Average Waiting Time :"+priority_scheduling.avg_waiting());
        priority_scheduling.Turnaround_time();
        System.out.println("Average Turnaround Time :"+priority_scheduling.avg_Turnaround());

        // SJF
        System.out.println("--------SJF Scheduling--------");
        SJF_Scheduling sjf_scheduling = new SJF_Scheduling(context);
        for(Process p : processes)
            sjf_scheduling.setProcess(p);

        System.out.println("Processes execution order ");
        sjf_scheduling.Scheduling();
        sjf_scheduling.waiting_time();
        System.out.println("Average Waiting Time :"+sjf_scheduling.avg_waiting());
        sjf_scheduling.Turnaround_time();
        System.out.println("Average Turnaround Time :"+sjf_scheduling.avg_Turnaround());



        // SRTF
        System.out.println("--------SRTF Scheduling--------");
        SRTF_Scheduling.averagetime(processes);


        //AGAT
        System.out.println("--------AGAT Scheduling--------");
        AGAT_Scheduling agat_scheduling = new AGAT_Scheduling(processes);
        agat_scheduling.Scheduling(quantum);



    }
}