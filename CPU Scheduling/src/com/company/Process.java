package com.company;

class Process {
    public String process_name;
    public int arrival_time, burst_time, priority,waiting_time,turnaround_time, agat_factor ,quantum;

    Process(String process_name, int arrival_time, int burst_time, int priority) {
        this.process_name = process_name;
        this.arrival_time = arrival_time;
        this.burst_time = burst_time;
        this.priority = priority;
    }

    void setFactor(int x)
    {
        agat_factor=x;
    }
}