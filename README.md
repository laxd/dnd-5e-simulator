D&D 5e Combat simulator
=====

A project designed to execute simulations of D&D combat with a given a list of characters, which produces a statistical analysis at the end describing the encounters.

Quick start
----
Compile/run tests: `gradle build`

To execute: `gradle run --args="path/to/json` (There is an example located in app/src/main/resources/test.json), e.g. `gradle run --args="src/main/resources/test.json"`

Inputs
----

Simulations can currently only be configured by using a json file, an example is located at app/src/main/resources/test.json

Each simulation requires at least 2 Characters (on opposing teams) which can be configured as follows:

```
{
      "name": "Magnus",
      "team": "Kill 'em all klub",
      "AC": 18,
      "levels": {
        "BARBARIAN": 5
      },
      "weapons": [
        {
          "name": "Greatsword",
          "damageType": "SLASHING",
          "diceDamage": 6,
          "diceCount": 2,
          "damageBonus": 1,
          "attackBonus": 1,
          "properties": [
            "Heavy"
          ],
          "range": 5,
          "priority": 1.0
        }
      ],
      "abilities": {
        "STRENGTH": 19,
        "DEXTERITY": 14,
        "CONSTITUTION": 16,
        "INTELLIGENCE": 6,
        "WISDOM": 13,
        "CHARISMA": 11
      },
      "hp": 50,
      "targetSelection": "lowestHealth"
    }
```

Outputs
----

The outcome of the simulations is handled by the `PostSimulationEvent`. The desired outcome can be set in the json file in the `post` section:

```
"post": [
    {
      "type": "printStats"
    },
    {
      "type": "logStats",
      "options": {
        "output": "simulation.csv",
        "typeFormat": "csv"
      }
    }
  ]
```

Events
----

Every action produces at least 1 `EncounterEvent`. These events are collated and can then be analysed at the end of the simulations.

Some actions may produce more than 1 `EncounterEvent`, e.g. a Melee attack will produce a `MeleeAttackEvent`, and if it hits, will also produce a `DamageEvent`.

Features
----

Features are what determines what happens when a character does something. A Character gains features from their class as defined in the json file