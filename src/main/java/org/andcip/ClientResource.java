package org.andcip;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import org.jboss.logging.Logger;

import org.andcip.entity.Client;

@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
@Path("/clients")
public class ClientResource {

    @Inject
    Logger log;
    
    @Inject
    EntityManager entityManager;

    @GET
    public List<Client> list() {
        log.debug("Called list");
        return entityManager.createNamedQuery("Clients.findAll", Client.class)
                .getResultList();
    }

    @GET
    @Path("{id}")
    public Client getClient(@PathParam("id") Long id) {
        log.debug("Called get client with id: {id} ");
        Client client = entityManager.find(Client.class, id);
        if ( client == null) {
            throw new WebApplicationException("Client does not exist", 404);
        }
        return client;
    }

    @POST
    @Transactional
    public Response createClient(Client client, @Context UriInfo uriInfo) {
        
        log.debug("Called create client with client: {client} ");
        if (client.getId() != null) {
            throw new WebApplicationException(400);
          
        }
        entityManager.persist(client);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(Long.toString(client.getId()));
        return Response.created(uriBuilder.build()).entity(client).build();
        
    }


    @PUT
    @Path("{id}")
    @Transactional
    public Client updateClient(@PathParam("id") Long id, Client client) {
        
        Client currentClient = entityManager.find(Client.class, id);
        currentClient.setName(client.getName());
        currentClient.setEmail(client.getEmail());

        return client;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteClient(@PathParam("id") Long id) {
        
        Client client = entityManager.getReference(Client.class, id);
        if ( client == null){
            throw new WebApplicationException("Client not found", 404);
        }
        entityManager.remove(client);
        return Response.ok().build();
    }

}