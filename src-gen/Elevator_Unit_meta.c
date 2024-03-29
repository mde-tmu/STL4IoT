/** Generated by itemis CREATE code generator. */

#include "Elevator_Unit_meta.h"
		
sc_string Elevator_Unit_meta_feature_names[] = {
	"<nothing>",
	"toggle",
	"stopped",
	"inUse",
	"direction.up",
	"direction.down",
	"level.current",
	"level.userLocation",
	"level.userInput",
	"input.value",
	"input.submit",
	"input.up",
	"input.down"
};

sc_string Elevator_Unit_meta_state_names[] = {
	"<nostate>",
	"main_region._Elevator_Unavailable_",
	"main_region._Elevator_Available_",
	"main_region._Elevator_Available_.activity._idle_",
	"main_region._Elevator_Available_.activity._active_",
	"main_region._Elevator_Available_.movement._upwards_",
	"main_region._Elevator_Available_.movement._downwards_",
	"main_region._Elevator_Available_.movement._standby_",
	"main_region._Elevator_Available_.level._ElevatorLevel_",
	"main_region._Elevator_Available_.level._ElevatorLevel_.r1._readingDataFromElevator_",
	"main_region._Elevator_Available_.input._gettingInput_",
	"main_region._Elevator_Available_.input._submitInput_"
};
