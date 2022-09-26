package me.minutz.l2m.procese.events.ha;

public abstract interface Cancellable
{
  public abstract boolean isCancelled();
  
  public abstract String getCancelMessage();
  
  public abstract void cancel(String message);
  
  public abstract void setCancelled(boolean cancel, String cancelMessage);
}
