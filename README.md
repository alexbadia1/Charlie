# Charlie

An old Windows 7 styled Blackjack Game

## Walkthrough

Due to grading, this project structure is Plugin Based. BadiaCharliePlugins and Charlie are interdependent on each other. This makes grading easiear as all I have to submit is BadiaCharliePlugins for each assignment.

  - Charlie/
      - The base of the project where the GameFrame and Server Side coded is.
      - This is where the core interfaces are also implmented to have basic functionality for things like:
          * The shoe that holds hards
          * The table where cards are dealt
          * The dealer who deals out cards
          * Players who have hands and can recieve cards
          * Core blackjack logic
          * etc.
  - badiaCharliePlugins/
      - The extra plugins that utilize the core interfaces of Charlie
          * Sidebets
          * Bots
          * Card Counting
          * etc.
  - SideBetOdds/
      - Not real part of the project
      - Used to run monte carlo simulations to verify Casino Sidebets odds to payout ratios.
      - Still a plugin in itself.
  - cyvis-0.9-bin
      - A tool used to show systematic complexity of a program considering various factors:
          * Branches/decision points
          * Lines of code
          * etc.
  - tmp/
      * Storage for the client and server outputlogs
