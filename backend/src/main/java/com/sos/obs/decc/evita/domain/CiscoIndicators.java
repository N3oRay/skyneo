package com.sos.obs.decc.evita.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @Author SLS --- on avril, 2019
 */


@NamedStoredProcedureQuery(
        name = "CiscoIndicators.getAll",
        procedureName = "sp_afficheurs_evita",
//        resultClasses = {CiscoIndicators.class},
        parameters = {@StoredProcedureParameter(mode = ParameterMode.OUT,name="res",type = List.class)}
)

@Entity
public class CiscoIndicators implements Serializable {

    public CiscoIndicators() {
    }


    @Id
    @Column(name = "IDESKGSKG")
    private Long id;

    @Column(name = "Logues")
    private int logged;

    @Column(name = "NotReady")
    private int notReady;

    @Column(name = "Ready")
    private int ready;

    @Column(name = "Disponibles")
    private int available;


    @Column(name = "En_conversation_Entrant")
    private int talkingIn;

    @Column(name = "En_conversation_Sortant")
    private int talkingOut;

    @Column(name = "En_conversation_Autre")
    private int talkingOther;

    @Column(name = "Appels_en_Attente")
    private int callsWaiting;
    @Column(name = "Duree_Max_Appels_en_Attente")
    private int waitingTime;

    @Column(name = "En_conversation")
    private int talking;

    @Column(name = "Post_Appel_Non_Pret")
    private int afterCallNotReady;

    @Column(name = "TPC")
    private int tpc;

    @Column(name = "Occupe_Autre")
    private int busyOther;
    @Column(name = "Reserve")
    private int reserved;
    @Column(name = "Inconnu")
    private int unknown;
    @Column(name = "En_conversation_Garde")
    private int hold;
    @Column(name = "Actif")
    private int actif;
    @Column(name = "En_pause")
    private int paused;

    @Column(name = "Interompu")
    private int interrupted;

    @Column(name = "Non_Actif")
    private int notActive;

    @Column(name = "RC_Defaut")
    private int rctDefault;

    @Column(name = "RC_THC")
    private int rcThc;

    @Column(name = "RC_Pause")
    private int rcPause;

    @Column(name = "RC_Formation")
    private int rcFormation;

    @Column(name = "RC_Management")
    private int rcManagement;

    @Column(name = "RC_Autres")
    private int rcOthers;

    @Column(name = "RCT_ReSkilled")
    private int rctReSkilled;

    @Column(name = "RCT_RONA")
    private int rctRona;

    @Column(name = "RCT_CTI_Failure")
    private int rctCtiFailure;

    @Column(name = "RCT_Missed_Tasks")
    private int rctMissedTasks;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLogged() {
        return logged;
    }

    public void setLogged(int logged) {
        this.logged = logged;
    }

    public int getNotReady() {
        return notReady;
    }

    public void setNotReady(int notReady) {
        this.notReady = notReady;
    }

    public int getReady() {
        return ready;
    }

    public void setReady(int ready) {
        this.ready = ready;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getTalkingIn() {
        return talkingIn;
    }

    public void setTalkingIn(int talkingIn) {
        this.talkingIn = talkingIn;
    }

    public int getTalkingOut() {
        return talkingOut;
    }

    public void setTalkingOut(int talkingOut) {
        this.talkingOut = talkingOut;
    }

    public int getTalkingOther() {
        return talkingOther;
    }

    public void setTalkingOther(int talkingOther) {
        this.talkingOther = talkingOther;
    }

    public int getCallsWaiting() {
        return callsWaiting;
    }

    public void setCallsWaiting(int callsWaiting) {
        this.callsWaiting = callsWaiting;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getTalking() {
        return talking;
    }

    public void setTalking(int talking) {
        this.talking = talking;
    }

    public int getAfterCallNotReady() {
        return afterCallNotReady;
    }

    public void setAfterCallNotReady(int afterCallNotReady) {
        this.afterCallNotReady = afterCallNotReady;
    }

    public int getTpc() {
        return tpc;
    }

    public void setTpc(int tpc) {
        this.tpc = tpc;
    }

    public int getBusyOther() {
        return busyOther;
    }

    public void setBusyOther(int busyOther) {
        this.busyOther = busyOther;
    }

    public int getReserved() {
        return reserved;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }

    public int getUnknown() {
        return unknown;
    }

    public void setUnknown(int unknown) {
        this.unknown = unknown;
    }

    public int getHold() {
        return hold;
    }

    public void setHold(int hold) {
        this.hold = hold;
    }

    public int getActif() {
        return actif;
    }

    public void setActif(int actif) {
        this.actif = actif;
    }

    public int getPaused() {
        return paused;
    }

    public void setPaused(int paused) {
        this.paused = paused;
    }

    public int getInterrupted() {
        return interrupted;
    }

    public void setInterrupted(int interrupted) {
        this.interrupted = interrupted;
    }

    public int getNotActive() {
        return notActive;
    }

    public void setNotActive(int notActive) {
        this.notActive = notActive;
    }

    public int getRctDefault() {
        return rctDefault;
    }

    public void setRctDefault(int rctDefault) {
        this.rctDefault = rctDefault;
    }

    public int getRcThc() {
        return rcThc;
    }

    public void setRcThc(int rcThc) {
        this.rcThc = rcThc;
    }

    public int getRcPause() {
        return rcPause;
    }

    public void setRcPause(int rcPause) {
        this.rcPause = rcPause;
    }

    public int getRcFormation() {
        return rcFormation;
    }

    public void setRcFormation(int rcFormation) {
        this.rcFormation = rcFormation;
    }

    public int getRcManagement() {
        return rcManagement;
    }

    public void setRcManagement(int rcManagement) {
        this.rcManagement = rcManagement;
    }

    public int getRcOthers() {
        return rcOthers;
    }

    public void setRcOthers(int rcOthers) {
        this.rcOthers = rcOthers;
    }

    public int getRctReSkilled() {
        return rctReSkilled;
    }

    public void setRctReSkilled(int rctReSkilled) {
        this.rctReSkilled = rctReSkilled;
    }

    public int getRctRona() {
        return rctRona;
    }

    public void setRctRona(int rctRona) {
        this.rctRona = rctRona;
    }

    public int getRctCtiFailure() {
        return rctCtiFailure;
    }

    public void setRctCtiFailure(int rctCtiFailure) {
        this.rctCtiFailure = rctCtiFailure;
    }

    public int getRctMissedTasks() {
        return rctMissedTasks;
    }

    public void setRctMissedTasks(int rctMissedTasks) {
        this.rctMissedTasks = rctMissedTasks;
    }
}
