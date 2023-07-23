package com.company;


import java.util.ArrayList;

class SRTF_Scheduling
{
    static void findWaitingTime(ArrayList<Process> processes, int [] waiting_time)
    {
        int[] rem_bt = new int[processes.size()];
        // Copy the burst time into copied_burst[]
        for (int i = 0; i < processes.size(); i++)
            rem_bt[i] = processes.get(i).burst_time;
        int completed = 0, t_sec = 0, min = Integer.MAX_VALUE; //max value=2147483647
        int shortest = 0, finish_t;
        boolean checker = false;
        while (completed != processes.size())
        {
            // find process with min remain time
            for (int j = 0; j < processes.size(); j++)
            {
                if ((processes.get(j).arrival_time <= t_sec) &&
                        (rem_bt[j] < min) && rem_bt[j] > 0)
                {
                    min = rem_bt[j]; // min burst time
                    shortest = j; //loop till we find the shortest process
                    checker = true;
                }
            }
            if (checker == false)
            {
                t_sec++;
                continue;
            }
            rem_bt[shortest]--; // decrease burst  time  one second;
            //   update
            min = rem_bt[shortest];
            if (min == 0) // process finished as burst time finished
                min = Integer.MAX_VALUE;
            // If process gets completely -> burst time=0;
            if (rem_bt[shortest] == 0)
            {
                // Increment completed
                completed++;
                checker = false;
                // calculate finish time;
                finish_t = t_sec + 1;
                waiting_time[shortest] = finish_t - processes.get(shortest).burst_time - processes.get(shortest).arrival_time;       // Calculate waiting time

                if (waiting_time[shortest] < 0)
                    waiting_time[shortest] = 0;
            }
            t_sec++;  // Increase  time with one second;
        }
    }

    static void findTurnAroundTime(ArrayList<Process> processes, int [] waiting_time, int [] turnaround_time)
    {
        // calculate turnaround_time -> adding burst time and waiting time
        for (int i = 0; i < processes.size(); i++)
            turnaround_time[i] = processes.get(i).burst_time + waiting_time[i];
    }

    // Method to calculate average time
    static void averagetime(ArrayList <Process> processes)
    {
        int waiting_time[] = new int[processes.size()], turnaround_time[] = new int[processes.size()];
        int total_wt = 0, total_tat = 0;
        // Function to find waiting time of all
        // processes
        SRTF_Scheduling.findWaitingTime(processes, waiting_time);
        // Function to find turn around time for
        // all processes
        findTurnAroundTime(processes, waiting_time, turnaround_time);
        // Display processes along with all
        // details
        System.out.println("Process " +
                "BT " + //Burst time
                "      WT " +//Waiting time
                "   TAt ");   //Turn around time
        // Calculate total waiting time and
        // total turnaround time
        for (int i = 0; i < processes.size(); i++)
        {
            total_wt = total_wt + waiting_time[i];
            total_tat = total_tat + turnaround_time[i];
            System.out.println(" " + processes.get(i).process_name + "\t\t"
                    + processes.get(i).burst_time + "\t\t " + waiting_time[i]
                    + "\t\t" + turnaround_time[i]);
        }
        System.out.println("Average waiting time = " +
                (float) total_wt / (float) processes.size());
        System.out.println("Average turn around time = " +
                (float) total_tat / (float) processes.size());
    }
}
