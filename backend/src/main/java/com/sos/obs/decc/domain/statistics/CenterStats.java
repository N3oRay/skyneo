package com.sos.obs.decc.domain.statistics;

import java.io.Serializable;
import java.util.Objects;


/**
 * @Author SLS --- on avr., 2019
 */

public class CenterStats implements Serializable {
    private static final long serialVersionUID = 1L;


    private int id;
    private int usersNbs;
    private int animationNbs;
    private int screensNbs;
    private int skillNbs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsersNbs() {
        return usersNbs;
    }

    public void setUsersNbs(int usersNbs) {
        this.usersNbs = usersNbs;
    }

    public int getAnimationNbs() {
        return animationNbs;
    }

    public void setAnimationNbs(int animationNbs) {
        this.animationNbs = animationNbs;
    }

    public int getScreensNbs() {
        return screensNbs;
    }

    public void setScreensNbs(int screensNbs) {
        this.screensNbs = screensNbs;
    }

    public int getSkillNbs() {
        return skillNbs;
    }

    public void setSkillNbs(int skillNbs) {
        this.skillNbs = skillNbs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CenterStats that = (CenterStats) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CenterStats{" +
                "id=" + id +
                ", usersNbs=" + usersNbs +
                ", animationNbs=" + animationNbs +
                ", screensNbs=" + screensNbs +
                ", skillNbs=" + skillNbs +
                '}';
    }

    public void update(SatatisticsValue item, String type) {
        switch (type) {
            case "ANIMATIONS":
                setAnimationNbs(item.getCount());
                break;
            case "SKILLS":
                setSkillNbs(item.getCount());
                break;
            case "SCREENS":
                setScreensNbs(item.getCount());
                break;
            case "USERS":
                setUsersNbs(item.getCount());
                break;
        }
    }
}
