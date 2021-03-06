package ar.fiuba.tdd.tp.client;

import ar.fiuba.tdd.tp.client.exception.ClientException;
import ar.fiuba.tdd.tp.client.input.ClientRequest;
import ar.fiuba.tdd.tp.client.input.handler.RequestHandler;
import ar.fiuba.tdd.tp.client.input.handler.RequestHandlerResolver;
import ar.fiuba.tdd.tp.client.input.supplier.ClientSupplier;
import ar.fiuba.tdd.tp.client.output.ClientResponse;
import ar.fiuba.tdd.tp.client.output.consumer.ClientConsumer;

import static ar.fiuba.tdd.tp.client.utils.Constants.PROCESS_COMMAND_ERROR;
import static ar.fiuba.tdd.tp.client.utils.Constants.WELCOME;

/**
 * A Client that handles the communication for a Game. Consumes requests from a {@link ClientSupplier}.
 * Those requests are handled by one {@link RequestHandler} and then this result is communicated
 * to the {@link ClientConsumer}.
 */
public class Client implements ClientListener {

    private final ClientCore core;
    private final RequestHandlerResolver resolver;
    private final ClientSupplier supplier;
    private final ClientConsumer consumer;

    public Client(ClientCore core, ClientSupplier supplier, ClientConsumer consumer,
                  RequestHandlerResolver requestHandlerResolver) {
        this.core = core;
        this.supplier = supplier;
        this.consumer = consumer;
        this.resolver = requestHandlerResolver;
    }

    public void run() {
        this.core.start();
        this.core.addListener(this);
        this.callConsumer(new ClientResponse(WELCOME));
        this.doRun();
    }

    private void doRun() {
        while (this.core.isRunning()) {
            this.processRequest(this.callSupplier());
        }
        this.core.removeListener(this);
    }

    private void processRequest(ClientRequest request) {
        try {
            this.resolver.resolve(request).handle(request);
        } catch (ClientException e) {
            callConsumer(new ClientResponse(PROCESS_COMMAND_ERROR + e.getMessage()));
        }
    }

    private ClientRequest callSupplier() {
        return this.supplier.getRequest();
    }

    private void callConsumer(ClientResponse event) {
        this.consumer.consume(event);
    }

    @Override
    public void listen(ClientResponse response) {
        this.callConsumer(response);
    }
}
