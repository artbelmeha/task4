package com.task04;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.syndicate.deployment.annotations.events.SqsTriggerEventSource;
import com.syndicate.deployment.annotations.lambda.LambdaHandler;

import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import com.syndicate.deployment.annotations.lambda.LambdaLayer;
import com.syndicate.deployment.model.ArtifactExtension;
import com.syndicate.deployment.model.DeploymentRuntime;

@LambdaHandler(lambdaName = "sqs_handler",
	roleName = "sqs_handler-role",
	isPublishVersion = true,
	aliasName = "${lambdas_alias_name}"
)
@SqsTriggerEventSource(targetQueue = "async_queue", batchSize = 1)
public class SqsHandler implements RequestHandler<SQSEvent, Void> {

	public Void handleRequest(SQSEvent event, Context context) {
		for (SQSMessage msg : event.getRecords()) {
		String message = msg.getBody();
		// Print the message content to CloudWatch
			context.getLogger().log("Message received: " + message);
			System.out.println("Message content: " + message);
		}
		return null;
	}
}
