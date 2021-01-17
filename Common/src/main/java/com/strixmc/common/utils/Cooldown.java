package com.strixmc.common.utils;

import lombok.Data;

@Data
public class Cooldown {

  private long start = System.currentTimeMillis();
  private long expire;
  private boolean notified;
  private long duration;

  public Cooldown(long duration) {
    this.duration = duration;
    this.expire = this.start + this.duration;
  }

  public boolean isNotified() {
    if (hasExpired()) {
      setNotified(true);
    }
    return notified;
  }

  public long getPassed() {
    return System.currentTimeMillis() - this.start;
  }

  public long getRemaining() {
    return this.expire - System.currentTimeMillis();
  }

  public boolean hasExpired() {
    return System.currentTimeMillis() - this.expire >= 0;
  }

  public String getTimerLeft() {
    return TimeUtil.millisToTimer(getRemaining());
  }

  public String getTimeLeft() {
    if (this.getRemaining() >= 60_000) {
      return TimeUtil.millisToRoundedTime(this.getRemaining());
    } else {
      return TimeUtil.millisToSeconds(this.getRemaining());
    }
  }

}
