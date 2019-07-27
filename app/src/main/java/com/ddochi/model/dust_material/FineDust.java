package com.ddochi.model.dust_material;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FineDust {
    @SerializedName("list")
    private List<Oblist> list;
    @SerializedName("parm")
    private Parm parm;

    public List<Oblist> getList() {
        return list;
    }

    public void setList(List<Oblist> list) {
        this.list = list;
    }

    public Parm getParm() {return parm;}
    public void setParm(Parm parm) {this.parm = parm;}
}
