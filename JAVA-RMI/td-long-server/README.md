# Rapport - TD long

## Hard Authentification

- I opted for the hard authentification, using a file with the extension .ser containing all serialized objects for the vote (list of users and candidates), concerning the unique rank of candidates, i serialized it as well along with the list of candidates and users because it needs to be kept and conserved despite restarting sessions so that any added candidate has the proper rank. i also decided to add the ability to modify the serialized file, that will be through deserializing it, modifying the objects that are inside of it, then serializing it again. Now we have the option to modify the data or keep the ones we have initially.
- i implemented the hard authentification and it's done in the following manner:
  - the server is started, the admin either chooses to modify existing data or provide his own
  - we create a Login and a VotingMaterial remote objects and rebind them to the registry we created
  - the client here rebinds himself on the registry after locating it
  - the client then looks up our Login object and gets a VotingMaterial object by calling the method requesVotingMaterial on it and passing himself as a paramater. The method checks whether he is a user or not and then returns the voting Material thus the authentification is implicit.

## Vote
