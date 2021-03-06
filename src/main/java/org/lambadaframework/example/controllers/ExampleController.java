package org.lambadaframework.example.controllers;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class ExampleController {


    static final Logger logger = Logger.getLogger(ExampleController.class);

    static class Entity {
        public int id = 1;
        public String name;

        public Entity(String name) {
            this.name = name;
        }
    }

    @GET
    public Response indexEndpoint(
    ) {
        logger.debug("Request got");
        return Response.status(200)
                .entity(new Entity("John doe"))
                .build();
    }

    @GET
    @Path("/{name}")
    public Response exampleEndpoint(
            @PathParam("name") String name
    ) {

        logger.debug("Request got");
        return Response.status(201)
                .entity(new Entity(name))
                .header("Access-Control-Allow-Origin", "*")
                .header("X-Test", "YZ")
                .build();
    }

    @GET
    @Path("/resource/{name}")
    public Response exampleSecondEndpoint(
            @PathParam("name") String name
    ) {

        logger.debug("Request got");
        return Response.status(201)
                .entity(new Entity(name))
                .build();
    }

    public static class NewEntityRequest {
        public String name;
    }

    /**
     * This controller uses automatically serialization of Request body to any POJO
     * @param requestEntity Request Entity
     * @return Response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/resource")
    public Response exampleSecondEndpointPost(
            NewEntityRequest requestEntity
    ) {

        logger.debug("Request got");
        return Response.status(201)
                .entity(new Entity(requestEntity.name))
                .build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/bits/{data}")
    /**
     * This example returns binary data as base64-encoded.
     * 
     * @param data
     * @return
     * @throws DecoderException
     */
    public byte[] exampleBinaryData(@PathParam("data") String data) throws DecoderException {
    	return Hex.decodeHex(data.toCharArray());
    }

    /**
     * We accept text/plain to avoid CORS OPTION pre-flight request.
     * 
     * @param data
     * @return
     */
	@Path("/text/{data}")
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	public Response test(String data) {
		return Response.status(200).entity(data).build();
	}

}
