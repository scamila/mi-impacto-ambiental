package crontask;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MessageJob implements Job {

    @Override
    public void execute(JobExecutionContext jobContext) throws JobExecutionException {
        JobDetail jobDetail = jobContext.getJobDetail();
        MessageTaskScheduler messageTaskScheduler = (MessageTaskScheduler) jobDetail.getJobDataMap().get("quartzSchedule");
        messageTaskScheduler.getOrganizacion().notificar(messageTaskScheduler.getEmail(), messageTaskScheduler.getWhatsAppMessage());
    }
}
