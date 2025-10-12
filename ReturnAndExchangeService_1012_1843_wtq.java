// 代码生成时间: 2025-10-12 18:43:50
package com.example.rest;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import io.quarkus.logging.Log;
import com.example.model.OrderItem;
import com.example.repository.OrderItemRepository;

@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReturnAndExchangeService {

    @Inject
    OrderItemRepository orderItemRepository;

    /**<ol>
     * Process a return and exchange request for an order item.
     *
     * @param orderItem The order item to be processed.
     * @return A response indicating the result of the operation.
     */
    @POST
    @Path("/processReturn")
    @Transactional
    public Response processReturn(OrderItem orderItem) {
        try {
            // Validate the input
            if (orderItem == null || orderItem.getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Order item ID is required.").build();
            }
            
            // Find the order item by ID
            OrderItem existingItem = orderItemRepository.findById(orderItem.getId());
            if (existingItem == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Order item not found.").build();
            }
            
            // Check if the item is eligible for return/exchange
            if (!existingItem.isEligibleForReturn()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Order item is not eligible for return/exchange.").build();            }
            
            // Process the return/exchange
            existingItem.setStatus("Returned/Exchanged");
            orderItemRepository.update(existingItem);

            // Return a success response
            return Response.ok().entity(existingItem).build();
        } catch (Exception e) {
            Log.error("Error processing return and exchange: ", e);
            return Response.serverError().entity("Error processing return and exchange.").build();
        }
    }
}
