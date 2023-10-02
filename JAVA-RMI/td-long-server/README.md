# Rapport - TD long

## Hard Authentification

I opted for the hard authentification, using a file with the extension .ser containing all serialized objects for the vote (list of users and candidates), concerning the unique rank of candidates, i serialized it as well along with the list of candidates and users because it needs to be kept and conserved despite restarting sessions so that any added candidate has the proper rank. i also decided to add the ability to modify the serialized file, that will be through deserializing it, modifying the objects that are inside of it, then serializing it again. Now we have the option to modify the data or keep the ones we have initially.
