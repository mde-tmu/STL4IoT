/** Generated by itemis CREATE code generator. */
/* Implements a standard statechart trace handler which adapts to the
 * YET tracing library.
 */


#include "Microwave_Unit_tracer.h"
#include "Microwave_Unit_meta.h"

#include <string.h>
#include <stdlib.h>


#ifndef SC_UNUSED
#define SC_UNUSED(P) (void)(P)
#endif

static sc_string default_scope_name = "Microwave_Unit";

static yet_value_serializer feature_value_serializer[15];



static void init_feature_value_serializer() {
	feature_value_serializer[0] = yet_serialize_void;
	feature_value_serializer[1] = yet_serialize_string;
	feature_value_serializer[2] = yet_serialize_bool;
	feature_value_serializer[3] = yet_serialize_bool;
	feature_value_serializer[4] = yet_serialize_void;
	feature_value_serializer[5] = yet_serialize_void;
	feature_value_serializer[6] = yet_serialize_bool;
	feature_value_serializer[7] = yet_serialize_void;
	feature_value_serializer[8] = yet_serialize_void;
	feature_value_serializer[9] = yet_serialize_void;
	feature_value_serializer[10] = yet_serialize_void;
	feature_value_serializer[11] = yet_int_serializer_function(sizeof(sc_integer), true);
	feature_value_serializer[12] = yet_serialize_void;
	feature_value_serializer[13] = yet_serialize_void;
	feature_value_serializer[14] = yet_serialize_bool;
}

static char* featureName(sc_integer featureId)
{
	return Microwave_Unit_meta_feature_names[featureId];
}

static char* featureValue(sc_integer featureId, const void* valuePtr, char* buf)
{
	return (feature_value_serializer[featureId])(valuePtr, buf);
}

static char* stateName(sc_integer stateId)
{
	return Microwave_Unit_meta_state_names[stateId];
}

static yet_error dispatchMessage(yet_scope *scope, yet_message * msg, char *fqn);

void microwave_Unit_init_sc_tracer(yet_sc_tracer *tracer, Microwave_Unit* machine, sc_string name)
{
	if (tracer != sc_null) {
		yet_init_sc_tracer(tracer, machine, &dispatchMessage);

		tracer->scope.name = default_scope_name;
		if (name != sc_null) {
			tracer->scope.name = name;
		}
		tracer->traceinfoProvider.featureName = featureName;
		tracer->traceinfoProvider.featureValue = featureValue;
		tracer->traceinfoProvider.stateName = stateName;
		
		machine->trace_handler = &(tracer->traceEventHandler);
	}
	init_feature_value_serializer();
}


/* Implementation of yet_handler callback function. Handles incoming stimuli and call appropriate state machine function. */
static yet_error dispatchMessage(yet_scope *scope, yet_message * msg, char *fqn)
{
	yet_sc_tracer* tracer = scope->instance;
	char* member;
	member = fqn;
	
	
	if(strcmp(member, "device.on") == 0) {
		/* Skip one message; this should not be re-raised on host */
		/* TODO: this is not optimal check if we can get rid of it */
		tracer->skip_raised_in_event++; 
		microwave_Unit_device_raise_on(tracer->machine);
		return 0;
	}
	if(strcmp(member, "device.off") == 0) {
		/* Skip one message; this should not be re-raised on host */
		/* TODO: this is not optimal check if we can get rid of it */
		tracer->skip_raised_in_event++; 
		microwave_Unit_device_raise_off(tracer->machine);
		return 0;
	}
	if(strcmp(member, "device.start") == 0) {
		/* Skip one message; this should not be re-raised on host */
		/* TODO: this is not optimal check if we can get rid of it */
		tracer->skip_raised_in_event++; 
		microwave_Unit_device_raise_start(tracer->machine);
		return 0;
	}
	if(strcmp(member, "device.pause") == 0) {
		/* Skip one message; this should not be re-raised on host */
		/* TODO: this is not optimal check if we can get rid of it */
		tracer->skip_raised_in_event++; 
		microwave_Unit_device_raise_pause(tracer->machine);
		return 0;
	}
	if(strcmp(member, "device.addTimer") == 0) {
		/* Skip one message; this should not be re-raised on host */
		/* TODO: this is not optimal check if we can get rid of it */
		tracer->skip_raised_in_event++; 
		microwave_Unit_device_raise_addTimer(tracer->machine);
		return 0;
	}
	if(strcmp(member, "device.resetTimer") == 0) {
		/* Skip one message; this should not be re-raised on host */
		/* TODO: this is not optimal check if we can get rid of it */
		tracer->skip_raised_in_event++; 
		microwave_Unit_device_raise_resetTimer(tracer->machine);
		return 0;
	}
	if(strcmp(member, "Door.open") == 0) {
		/* Skip one message; this should not be re-raised on host */
		/* TODO: this is not optimal check if we can get rid of it */
		tracer->skip_raised_in_event++; 
		microwave_Unit_Door_raise_open(tracer->machine);
		return 0;
	}
	if(strcmp(member, "Door.close") == 0) {
		/* Skip one message; this should not be re-raised on host */
		/* TODO: this is not optimal check if we can get rid of it */
		tracer->skip_raised_in_event++; 
		microwave_Unit_Door_raise_close(tracer->machine);
		return 0;
	}
	if(strcmp(member, "message") == 0) {
		sc_string converted = msg->value;
		microwave_Unit_set_message(tracer->machine, converted);
		return 0;
	}
	if(strcmp(member, "food_inside_sensed") == 0) {
		sc_boolean converted = (msg->value[0] == 't');
		microwave_Unit_set_food_inside_sensed(tracer->machine, converted);
		return 0;
	}
	if(strcmp(member, "in_use") == 0) {
		sc_boolean converted = (msg->value[0] == 't');
		microwave_Unit_set_in_use(tracer->machine, converted);
		return 0;
	}
	if(strcmp(member, "device.isOn") == 0) {
		sc_boolean converted = (msg->value[0] == 't');
		microwave_Unit_device_set_isOn(tracer->machine, converted);
		return 0;
	}
	if(strcmp(member, "device.timer") == 0) {
		sc_integer converted = (sc_integer)atol(msg->value);
		microwave_Unit_device_set_timer(tracer->machine, converted);
		return 0;
	}
	if(strcmp(member, "Door.closed") == 0) {
		sc_boolean converted = (msg->value[0] == 't');
		microwave_Unit_Door_set_closed(tracer->machine, converted);
		return 0;
	}
	
	
	return YET_ERR_INVALID_KEY;
}

