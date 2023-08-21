package crontask;

import domain.organizacion.Organizacion;
import sendmessage.EmailConfig;
import sendmessage.WhatsAppConfig;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import java.util.concurrent.CountDownLatch;

import static util.Constants.CRON_TASK_MESSAGE;

public class MessageTaskScheduler {

    private CountDownLatch latch = new CountDownLatch(1);

    private EmailConfig emailConfig;

    private WhatsAppConfig whatsAppConfig;

    private Organizacion organizacion;

    public void fireJob() throws SchedulerException, InterruptedException {
        SchedulerFactory schedFact = new StdSchedulerFactory();
        Scheduler scheduler = schedFact.getScheduler();
        scheduler.start();

        JobBuilder jobBuilder = JobBuilder.newJob(MessageJob.class);
        JobDataMap data = new JobDataMap();
        data.put("quartzSchedule", this);


        JobDetail detail = jobBuilder.usingJobData(data)
                .withIdentity("myJob", "group1")
                .build();


        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule(CRON_TASK_MESSAGE))
                .build();

        scheduler.scheduleJob(detail, trigger);
        latch.await();
        System.out.println("All triggers executed. Shutdown scheduler");
        scheduler.shutdown();
    }

    public EmailConfig getEmail() {
        return emailConfig;
    }

    public void setEmail(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }

    public WhatsAppConfig getWhatsAppMessage() {
        return whatsAppConfig;
    }

    public void setWhatsAppMessage(WhatsAppConfig whatsAppConfig) {
        this.whatsAppConfig = whatsAppConfig;
    }

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }
}
