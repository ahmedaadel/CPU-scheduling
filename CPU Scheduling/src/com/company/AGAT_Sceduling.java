package com.company;

import java.util.ArrayList;
import java.util.Queue;

class AGAT_Scheduling {
    ArrayList<Process> processes ;
    Queue<Process> ready;
    ArrayList<Process> deadlist=new ArrayList<>();
    public int v1;

    AGAT_Scheduling(ArrayList<Process> processes)
    {
        this.processes=processes;
        int max=0;
        for(Process p : processes)
        {
            if(p.arrival_time>=max)
                max=p.arrival_time;
        }
        v1= max>10? max/10 : 1;
    }


    int findAgatFactor(Process p , int rem_burst , int v2)
    {
        int x = 10-p.priority;
        int y = p.arrival_time/v1;
        int z = rem_burst/v2;
        return x+y+z;
    }

    int max_rem_burstV2(int [] rem_bt)
    {
        int max=0;
        for(int i=0;i<rem_bt.length;i++)
        {
            if(rem_bt[i]>=max)
                max=rem_bt[i];
        }

        return max>10? max/10 : 1;
    }


    // Method to find the waiting time for all
    // processes
    void findWaitingTime(int quantum)
    {

        // Make a copy of burst times bt[] to store remaining
        // burst times.
        int [] rem_bt = new int[processes.size()];
        for (int i = 0 ; i < processes.size() ; i++)
            rem_bt[i] =  processes.get(i).burst_time;

        int curr_time = 0; // Current time

        // Keep traversing processes in round robin manner
        // until all of them are not done.
        while(true)
        {


            boolean done = true;

            // Traverse all processes one by one repeatedly
            for (int i = 0 ; i < processes.size(); i++)
            {
                /*
                int v2 = max_rem_burstV2(rem_bt);
                agats[i] = findAgatFactor(processes.get(i),rem_bt[i],v2);
                processes.get(i).setFactor(agats[i]);
                */
                // If burst time of a process is greater than 0
                // then only need to process further
                if (rem_bt[i] > 0 && processes.get(i).arrival_time<=curr_time) {
                    done = false; // There is a pending process

                    if (rem_bt[i] > quantum) {
                        // Increase the value of curr_time i.e. shows
                        // how much time a process has been processed
                        curr_time += quantum;

                        // Decrease the burst_time of current process
                        // by quantum
                        rem_bt[i] -= quantum;
                    }

                    // If burst time is smaller than or equal to
                    // quantum. Last cycle for this process
                    else {
                        // Increase the value of curr_time i.e. shows
                        // how much time a process has been processed
                        curr_time = curr_time + rem_bt[i];

                        // Waiting time is current time minus time
                        // used by this process
                        processes.get(i).waiting_time = curr_time - processes.get(i).burst_time - processes.get(i).arrival_time;

                        // As the process gets fully executed
                        // make its remaining burst time = 0
                        rem_bt[i] = 0;
                        deadlist.add(processes.get(i));
                    }
                }

            }

            // If all processes are done
            if (done)
                break;
        }
    }

    // Method to calculate turn around time
    void findTurnAroundTime()
    {
        // calculating turnaround time by adding
        // bt[i] + wt[i]
        for (Process p : processes)
            p.turnaround_time=p.burst_time+p.waiting_time;
    }

    // Method to display details of each process and average time
    void Scheduling(int quantum)
    {
        // Function to find waiting time of all processes
        findWaitingTime(quantum);

        // Function to find turn around time for all processes
        findTurnAroundTime();

        //execution order
        System.out.println("Execution order: ");
        for(Process p : deadlist)
            System.out.print(p.process_name+" ");
        System.out.println();



        //Details of each process
        for(Process p : processes)
        {
            System.out.println(p.process_name +" waitingTime:" +p.waiting_time+
                    " turnaroundTime:"+ p.turnaround_time );
            System.out.println("----------------------");
        }


        // Calculate total waiting time and total turn
        // around time
        int total_wt = 0, total_tat = 0;
        for (Process p : processes)
        {
            total_wt = total_wt + p.waiting_time;
            total_tat = total_tat + p.turnaround_time;
        }

        System.out.println("Average waiting time = " + (float)total_wt / (float)processes.size());
        System.out.println("Average turn around time = " + (float)total_tat / (float)processes.size());
    }
}