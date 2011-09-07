/*
 * Copyright (c) 2011 Khanh Tuong Maudoux <kmx.petals@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package fr.soat.demo.jmx;

import javax.management.Notification;

import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;

/**
 * @author khanh
 * 
 */
public class SimpleSpringBean implements NotificationPublisherAware {
    private int notificationIndex;
    private NotificationPublisher notificationPublisher;
    private String someValue = "Nada";

    public String getSomeValue() {
        return this.someValue;
    }

    public void setSomeValue(String aSomeValue) {
        notificationPublisher.sendNotification(buildNotification(this.someValue, aSomeValue));
        this.someValue = aSomeValue;
    }

    /**
     * Generate a Notification that will ultimately be published to interested listeners.
     * 
     * @param aOldValue
     *            Value prior to setting of new value.
     * @param aNewValue
     *            Value after setting of new value.
     * @return Generated JMX Notification.
     */
    private Notification buildNotification(String aOldValue, String aNewValue) {
        String notificationType = "fr.soat.demo";
        // String message = "Converting " + aOldValue + " to " + aNewValue;
        Notification notification = new Notification(notificationType, this, notificationIndex++, System.currentTimeMillis(), String.valueOf(notificationIndex));
        notification.setUserData("Example #" + notificationIndex);
        return notification;
    }

    /**
     * This is the only method required to fully implement the NotificationPublisherAware interface. This method allows Spring to inject a NotificationPublisher
     * into an instance of this class.
     * 
     * @param aPublisher
     *            The NotificationPublisher that Spring injects..
     */
    public void setNotificationPublisher(NotificationPublisher aPublisher) {
        this.notificationPublisher = aPublisher;
    }
}
