package com.company;

import java.util.ArrayList;

class Priority_Scheduling {

    ArrayList<Process> Processes;
    ArrayList<Process> timing_Processes;
    ArrayList<Process> Process_order;
    int Context;
    ArrayList<Integer> waiting_times ;
    ArrayList<Integer> Turnaround_times;

    Priority_Scheduling(int context) {
        this.Processes = new ArrayList<Process>();
        this.timing_Processes = new ArrayList<Process>();
        this.waiting_times=new ArrayList<Integer>();
        this.Turnaround_times=new ArrayList<Integer>();
        this.Context = context;
        Process_order = new ArrayList<>();
    }

    void setProcess(Process p) {
        Processes.add(p);
        timing_Processes.add(p);
    }

    ArrayList<Process> update_priority(ArrayList<Process> procc) // for starvation problem
    {
        for (int i = 0; i < procc.size(); i++) {
            procc.get(i).priority = procc.get(i).priority - 1;
        }
        return procc;
    }

    void update_Process_queue (ArrayList<Process> remover)
    {
        for (int vv=0;vv<remover.size();vv++)
        {
            for (int jj=0;jj<Processes.size();jj++)
            {
                if (Processes.get(jj)==remover.get(vv))
                    Processes.remove(jj);
            }

        }
    }

    void Scheduling() {
        int x = Processes.size();
        int current_time = 0;
        ArrayList<Process> queue = new ArrayList<Process>();
        ArrayList<Process> remover = new ArrayList<Process>();
        for (int i = 0; i < x; i++) {
            if (i == 0) {
                int arrival = 100000000;
                for (int q = 0; q < Processes.size(); q++) //for checking the less time alocated now
                {
                    if (arrival > Processes.get(q).arrival_time) {
                        arrival = Processes.get(q).arrival_time;
                    }

                }

                for (int w = 0; w < Processes.size(); w++) //to make an array of same time Processes and remove it from the original array
                {
                    if (Processes.get(w).arrival_time == arrival) {
                        queue.add(Processes.get(w));
                        remover.add(Processes.get(w));

                    }

                }
                update_Process_queue(remover);
                remover.clear();
                Process p = queue.get(0);
                for (int l = 0; l < queue.size(); l++) {

                    if (p.priority > queue.get(l).priority) {
                        p = queue.get(l);
                    }
                }
                Process_order.add(p);

                current_time = arrival + p.burst_time;
                queue.remove(p);

                queue = update_priority(queue);
            }

            for (int n = 0; n < Processes.size(); n++) {
                if (Processes.get(n).arrival_time <= current_time) {
                    queue.add(Processes.get(n));
                    remover.add(Processes.get(n));
                }
            }
            update_Process_queue(remover);
            remover.clear();

            if (!queue.isEmpty()) {
                Process t = queue.get(0);
                for (int l = 0; l < queue.size(); l++) {

                    if (t.priority > queue.get(l).priority) {
                        t = queue.get(l);
                    }
                }
                Process_order.add(t);
                current_time += Context + t.burst_time;
                queue.remove(t);

                queue = update_priority(queue);
            }

        }

        for (int i = 0; i < Process_order.size(); i++) {
            System.out.print(Process_order.get(i).process_name);
            System.out.print(" ");
        }
        System.out.println();

    }

    int ProcessEnding(Process x) // to find when the Process end
    {
        int order = 0, end_time = Process_order.get(0).arrival_time;
        for (int i = 0; i < Process_order.size(); i++) {
            if (Process_order.get(i) == x) {
                order = i;
                break;
            }
        }
        if (order == 0) {
            return Process_order.get(0).burst_time;
        }
        for (int i = 0; i <= order; i++) {
            end_time += Process_order.get(i).burst_time + Context;
        }
        return end_time - Context;
    }


    void waiting_time() {
        Process ss;
        int wait;

        for (int i = 0; i < Process_order.size(); i++) {
            ss=Process_order.get(i);
            int end_time = ProcessEnding(ss);
            int start_time = Process_order.get(i).arrival_time;
            wait=end_time - start_time - Process_order.get(i).burst_time;
            waiting_times.add(wait);

        }
        System.out.println("the waiting times"+waiting_times);

    }

    void Turnaround_time() {
        Process ss;

        for (int i = 0; i < Process_order.size(); i++) {
            ss=Process_order.get(i);
            int end_time = ProcessEnding(ss);
            int start_time = Process_order.get(i).arrival_time;
            Turnaround_times.add(end_time - start_time);

        }
        System.out.println("the Turnaround times"+Turnaround_times);

    }
    float avg_waiting()
    {
        float total=0;
        for (int i=0;i<waiting_times.size();i++)
        {
            total+=waiting_times.get(i);
        }

        return total/waiting_times.size();
    }
    float avg_Turnaround()
    {
        float total=0;
        for (int o=0;o<Turnaround_times.size();o++)
        {
            total+=Turnaround_times.get(o);
        }

        return total/Turnaround_times.size();
    }
}