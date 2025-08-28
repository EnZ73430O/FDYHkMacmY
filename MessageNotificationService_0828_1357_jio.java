// 代码生成时间: 2025-08-28 13:57:55
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;
import io.vertx.mutiny.core.eventbus.MessageConsumer;

@Path("/notification")
@ApplicationScoped
public class MessageNotificationService {

    @Inject
    Vertx vertx;

    private final String address = "message.address";

    @POST
    @Path("/send")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> sendMessage(String message) {
        // Use Vert.x to send a message to the event bus
        Uni<Message<Object>> sendMessage = vertx.eventBus().send(address, message)
                .onItem().transform(Message::body);

        return sendMessage
                .onItem().transform(msg -> Response.status(Response.Status.ACCEPTED).entity(msg).build())
                .onFailure().recoverWithItem(t -> Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(t.getMessage()).build());
    }

    @POST
    @Path("/listen")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> listenForMessages() {
        // Set up a consumer on the event bus
        return vertx.eventBus().consumer(address)
                .onItem().transform(MessageConsumer::registration)
                .onItem().invoke(registration -> registration.resume())
                .replaceWith(Response.status(Response.Status.OK).entity("Started listening on address: " + address).build());
    }

    @POST
    @Path("/stop-listening\)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> stopListening(String consumerId) {
        // Remove the consumer from the event bus
        Uni<Void> stopConsuming = vertx.eventBus().consumer(address, consumerId)
                .onItem().transform(MessageConsumer::registration)
                .onItem().invoke(registration -> registration.unregister());

        return stopConsuming
                .onItem().transform(v -> Response.status(Response.Status.OK).entity("Stopped listening on address: " + address).build())
                .onFailure().recoverWithItem(t -> Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(t.getMessage()).build());
    }
}
