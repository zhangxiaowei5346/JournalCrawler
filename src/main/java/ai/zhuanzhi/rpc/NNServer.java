package ai.zhuanzhi.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;

public class NNServer implements RPCProtocol {

    public static void main(String[] args) throws IOException {
        RPC.Server server = new RPC.Builder(new Configuration())
                .setBindAddress("localhost")
                .setPort(8888)
                .setProtocol(RPCProtocol.class)
                .setInstance(new NNServer())
                .build();

        System.out.println("server begin to work");
        server.start();
    }

    @Override
    public void mkdirs(String path) {
        System.out.println("server has accepted client request " + path);
    }
}
