package com.example.kalvi.dto;

import java.util.ArrayList;
import java.util.List;

public class ModuleDTO {

    public ModuleDTO(){
        this.topics = new ArrayList<>();
    }
    private Long id;

    private String name;

    private List<TopicDTO> topics;

    private int duration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TopicDTO> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicDTO> topics) {
        this.topics = topics;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
