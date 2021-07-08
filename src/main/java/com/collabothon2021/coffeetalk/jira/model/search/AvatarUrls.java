package com.collabothon2021.coffeetalk.jira.model.search; 
import com.fasterxml.jackson.annotation.JsonProperty; 
public class AvatarUrls{
    @JsonProperty("48x48") 
    public String _48x48;
    @JsonProperty("24x24") 
    public String _24x24;
    @JsonProperty("16x16") 
    public String _16x16;
    @JsonProperty("32x32") 
    public String _32x32;
}
