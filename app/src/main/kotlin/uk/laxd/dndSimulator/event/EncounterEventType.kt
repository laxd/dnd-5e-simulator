package uk.laxd.dndSimulator.event

enum class EncounterEventType {
    ENCOUNTER_START,
    ENCOUNTER_FINISH,
    ROUND_START,
    TURN_START,
    TURN_END,
    MELEE_ATTACK,
    BONUS_ACTION,
    FREE_ACTION,
    DAMAGE,
    DEATH,
    HOLD_TURN
    //    SPELL_ATTACK,
    //    SPELL_HEALING,
    //    ITEM_USE,
    //    MOVEMENT;
}