package app.core;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import app.core.beans.Trainer;

@Configuration
@ComponentScan
@PropertySource("application.properties")
public class App {

	public static void main(String[] args) {

		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(App.class)) {

			Trainer tennis = ctx.getBean("tennisTrainer", Trainer.class);
			Trainer swimm = ctx.getBean("swimmingTrainer", Trainer.class);
			Trainer run = ctx.getBean("runningTrainer", Trainer.class);
//			SwimmingTrainer swim = ctx.getBean(SwimmingTrainer.class);
//			TennisTrainer tennis = ctx.getBean(TennisTrainer.class);
//			RunningTrainer run = ctx.getBean(RunningTrainer.class);
//		
			System.out.println(tennis.getTrainProgram());
			System.out.println(swimm.getTrainProgram());
			System.out.println(run.getTrainProgram());
		}

	}
}