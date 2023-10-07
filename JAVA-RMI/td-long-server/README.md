# Report - Long TD

## How to Use the App
the RMI registry is created inside the server main.
### Server
- Start the server
- Choose to use existing data or modify data
- Type in "start" to begin the vote
- Type in "end" to end the vote

### Client
#### First Time Voting
- Type in credentials
- Wait for the vote to start
- Receive and confirm your OTP
- Vote

#### Revote
- Type in credentials
- Type in previous OTP
- Receive new OTP and confirm it
- Vote

## Choices Made

### Candidate Class
- Implemented a `CandidateWithPitch` class that extends `Candidate`, includes a String for text pitch or a video URL

### Hard Authentication
- Opted for hard authentication, utilizing a `.ser` file containing all serialized objects for the vote (lists of users and candidates)
- Enabled modification of the serialized file through deserialization, object modification, and re-serialization
- At server start, admin chooses to modify existing data or use initial data
- Created `Login` and `VoteManager` remote objects and rebound them to the newly created registry
- Clients rebind themselves on the registry after locating it
- Clients look up the `Login` object and receive a `VoteManager` object by calling `requestVotingMaterial` on it, passing themselves as parameters, thus achieving imbricated authentication
- `VoteManager` contains all functionalities related to voting, including OTP generation

### OTP Implementation
- After the vote starts, a Client receives his OTP, a necessity for vote casting
- The OTP is retained if not used for voting.

### Vote
- Server admin starts and ends the vote by typing "start" and "end" respectively
- `VoteManager` remote object contains all vote-related methods and data
- Revoting necessitates the deletion of the initial vote
- Every cast vote, along with the timestamp, is displayed on the server terminal
- Clients can check current voting results after voting or after the voting ends

### Exceptions
- Encountered in cases of bad credentials, incorrect OTP, incorrect score input by the client, or vote ending before a user completes their vote

### Difficulties Met
- Implementing real-time vote tracking for clients was challenging; resolved by creating a remote interface for immediate vote status checking
- Vote logging on the server was addressed via a callback mechanism, ensuring instant recording and display of each cast vote on the server terminal
