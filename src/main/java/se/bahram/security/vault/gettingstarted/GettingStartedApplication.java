package se.bahram.security.vault.gettingstarted;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;

public class GettingStartedApplication {



    public static void main(String[] args) throws VaultException {
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
    }
}
