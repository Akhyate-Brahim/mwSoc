# Java remote method invocation (RMI)

# Java RMI : Guide approfondi

Java RMI (Java Remote Method Invocation) est une technologie Java qui permet aux objets d'une JVM d'invoquer des méthodes sur des objets situés dans une autre JVM.

## 1. Concepts fondamentaux

### Stub & Skeleton

- **Stub** : C'est le proxy côté client pour un objet distant. Il implémente la même interface que l'objet distant et est responsable de la sérialisation des paramètres et de la communication réseau. En réalité, c'est le Stub que le client invoque, qui transmet ensuite l'appel à l'objet distant.

- **Skeleton** : Présent côté serveur, le Skeleton désérialise les requêtes entrantes, invoque la méthode appropriée sur l'objet distant, puis sérialise la réponse pour la renvoyer au client.

### RMI Registry

- C'est essentiellement un annuaire pour les objets RMI. Les objets distants s'enregistrent auprès du RMI Registry, et les clients recherchent ces objets en utilisant des noms logiques.

## 2. Fonctionnement et Interactions

### Cycle de vie d'un appel RMI

[sequence diagram](./rmi_diagram.png)

1. Le serveur crée l'objet distant et l'enregistre auprès du RMI Registry.
2. Le client recherche l'objet distant par son nom dans le RMI Registry.
3. Le RMI Registry retourne le Stub de l'objet distant au client.
4. Le client invoque une méthode sur le Stub.
5. Le Stub sérialise l'appel et les arguments, puis les envoie via le réseau.
6. Le Skeleton, côté serveur, reçoit la demande, la désérialise et invoque la méthode appropriée sur l'objet distant.
7. La réponse est sérialisée et renvoyée au client via le réseau.
8. Le Stub côté client désérialise la réponse et la retourne au code client.

### Gestion des erreurs

Les appels RMI peuvent échouer pour diverses raisons:

- **Erreurs réseau** : Pannes de réseau, latence, etc.
- **Exceptions côté serveur** : Si l'objet distant rencontre une erreur pendant l'exécution.
- **Problèmes de sérialisation** : Si un objet ne peut pas être sérialisé ou désérialisé correctement.

### Nature synchrone des appels RMI

Les appels RMI sont par défaut synchrones. Cela signifie que le client attend une réponse de l'objet distant avant de continuer. Cette nature synchronisée nécessite une gestion soignée des délais d'attente et des erreurs pour éviter les blocages indésirables.

## 3. Multithreading et Performance

Le serveur RMI utilise un pool de threads pour gérer plusieurs requêtes client simultanément. Cela permet d'améliorer la performance et la réactivité. Si un seul thread était utilisé, les requêtes concurrentes seraient mises en file d'attente, ce qui pourrait entraîner des délais significatifs.

Il est également crucial de noter que le code exécuté côté serveur doit être conçu pour être thread-safe, car plusieurs threads pourraient accéder à des ressources partagées simultanément.

## 4. Autres considérations

### RemoteException

Toutes les méthodes définies dans une interface `Remote` doivent déclarer la possibilité de lever une `RemoteException`. Cela garantit que les clients gèrent correctement les éventuelles erreurs ou problèmes de communication.

### Téléchargement dynamique de classes

Java RMI supporte le téléchargement dynamique de classes. Cela signifie qu'un client peut recevoir un objet d'une classe qu'il n'a jamais rencontrée auparavant, à condition que cette classe soit accessible depuis un serveur de classes accessible.

### Sécurité

La sécurisation des communications RMI est essentielle, notamment en utilisant RMI sur SSL. Il faut également être prudent avec le téléchargement dynamique de classes pour éviter les attaques de type "man-in-the-middle".






