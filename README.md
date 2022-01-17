# Getting Started With Hashicorp Vault
This project aims to get started with [hashicorp vault](https://www.vaultproject.io/) project.
HashiCorp Vault is an open source tool to manage secrets and protect sensitive data.

## Install
This part aims to install Vault for both client and server. You can visit [official getting started page](https://learn.hashicorp.com/collections/vault/getting-started) to more details.

First, install HashiCorp tap.
```shell
$ brew tap hashicorp/tap
```
Now, Install VAult with ``hashicorp/tap/vault``.
```shell
$ brew install hashicorp/tap/vault
```
To Verify the Installation execute ``vault`` in the shell.
```shell
$ vault
```
Vault is installed and ready to go!

## Start the Dev Server
The **dev server** is a built-in, pre-configured server 
- Useful for local development, testing, and exploration
- not very secure
- everything is stored in-memory
- Vault is automatically insealed
- ...

To start the Vault dev server, run:
```shell
$ vault server -dev
```
The output should be similar to this:
```shell
==> Vault server configuration:

             Api Address: http://127.0.0.1:8200
                     Cgo: disabled
         Cluster Address: https://127.0.0.1:8201
              Go Version: go1.17.5
              Listener 1: tcp (addr: "127.0.0.1:8200", cluster address: "127.0.0.1:8201", max_request_duration: "1m30s", max_request_size: "33554432", tls: "disabled")
               Log Level: info
                   Mlock: supported: false, enabled: false
           Recovery Mode: false
                 Storage: inmem
                 Version: Vault v1.9.2
             Version Sha: f4c6d873e2767c0d6853b5d9ffc77b0d297bfbdf
```
and followed by:
```shell
WARNING! dev mode is enabled! In this mode, Vault runs entirely in-memory
and starts unsealed with a single unseal key. The root token is already
authenticated to the CLI, so you can immediately begin using Vault.

You may need to set the following environment variable:

    $ export VAULT_ADDR='http://127.0.0.1:8200'

The unseal key and root token are displayed below in case you want to
seal/unseal the Vault or re-authenticate.

Unseal Key: rMsJW1e5mT/PsS9NzkF9ePhv27Kv69baL7U1OBdzn8A=
Root Token: s.Hm4OawovfmkilRd0wHwZUC3O

Development mode should NOT be used in production installations!
```
With the dev server started, perform th following
1. Launch a new terminal session.
2. Copy and run the ``export VAULT_ADDR ...``
```shell
$ export VAULT_ADDR='http://127.0.0.1:8200'
```
3. Save the unseal key somewhere.
4. Set the ``VAULT_TOKEN`` environment variable value to the generated **Root Token value displayed in the terminal output.
```shell
$ export VAULT_TOKEN="s.Hm4OawovfmkilRd0wHwZUC3O"
```
### Verify the Server is Running
Verify the server is running by running the ``vault status`` command. If it ran successfully, the output should look like the following:
```shell
$ vault status

Key             Value
---             -----
Seal Type       shamir
Initialized     true
Sealed          false
Total Shares    1
Threshold       1
Version         1.9.2
Storage Type    inmem
Cluster Name    vault-cluster-8ed4b8e7
Cluster ID      c7b66b32-5303-7306-44f0-0fd83c8c08a6
HA Enabled      false
```

## The First Secret
When running Vault in **dev** mode, **Key/Value** v2 engine is enabled at ``secret/`` path.  
Use the ``vault kv <subcommand> [options] [args]`` command to interact with Key/Value secret engine.  

### Get command help
```shell
$ vault kv -help
```

### Write a secret
Before you begin, check the command help.
```shell
$ vault kv put -help
```
Now, write a secret ``username`` with value of ``admin`` to the path ``secret/admin_user`` using the ``vault kv put`` command. 
```shell
$ vault kv put secret/admin_user username=admin
```
You can ever write multiple pieces of data.
```shell
vault kv put secret/admin_user username=admin password=admin
```
### Read a secret
As you might expect, secrets can be retrieved with ``vault kv get <pathe>``
```shell
$ vault kv get secret/admin_user
```

## Let's Java It!
It's the time to get your hand a little dirty by java codes.

### Maven dependency
Add this dependency to your ``pom.xml`` file.
```xml
<dependency>
    <groupId>com.bettercloud</groupId>
    <artifactId>vault-java-driver</artifactId>
    <version>5.1.0</version>
</dependency>
```

### Java Code
**Cautious:** than it's better to put the ``address`` and ``token`` in the environmental variables. **this code is not secret at all!**
```java
final VaultConfig config = new VaultConfig()
                .address("http://127.0.0.1:8200")
                .token("s.Hm4OawovfmkilRd0wHwZUC3O")
                .build();
final Vault vault = new Vault(config);
String password = vault.logical()
        .read("secret/admin_user")
        .getData()
        .get("password");
System.out.println("password = " + password);
```