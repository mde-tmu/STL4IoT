/** Generated by itemis CREATE code generator. */
/* Implements a standard statechart trace handler which adapts to the
 * YET tracing library.
 */


#include "Controller_Component_tracer.h"
#include "Controller_Component_meta.h"

#include <string.h>
#include <stdlib.h>


#ifndef SC_UNUSED
#define SC_UNUSED(P) (void)(P)
#endif

static sc_string default_scope_name = "Controller_Component";

static yet_value_serializer feature_value_serializer[7];



static void init_feature_value_serializer() {
	feature_value_serializer[0] = yet_serialize_void;
	feature_value_serializer[1] = yet_serialize_void;
	feature_value_serializer[2] = yet_serialize_void;
	feature_value_serializer[3] = yet_serialize_bool;
	feature_value_serializer[4] = yet_serialize_bool;
	feature_value_serializer[5] = yet_serialize_void;
	feature_value_serializer[6] = yet_serialize_bool;
}

static char* featureName(sc_integer featureId)
{
	return Controller_Component_meta_feature_names[featureId];
}

static char* featureValue(sc_integer featureId, const void* valuePtr, char* buf)
{
	return (feature_value_serializer[featureId])(valuePtr, buf);
}

static char* stateName(sc_integer stateId)
{
	return Controller_Component_meta_state_names[stateId];
}

static yet_error dispatchMessage(yet_scope *scope, yet_message * msg, char *fqn);

void controller_Component_init_sc_tracer(yet_sc_tracer *tracer, Controller_Component* machine, sc_string name)
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
	
	
	if(strcmp(member, "off") == 0) {
		/* Skip one message; this should not be re-raised on host */
		/* TODO: this is not optimal check if we can get rid of it */
		tracer->skip_raised_in_event++; 
		controller_Component_raise_off(tracer->machine);
		return 0;
	}
	if(strcmp(member, "on") == 0) {
		/* Skip one message; this should not be re-raised on host */
		/* TODO: this is not optimal check if we can get rid of it */
		tracer->skip_raised_in_event++; 
		controller_Component_raise_on(tracer->machine);
		return 0;
	}
	if(strcmp(member, "Actuator.trigger") == 0) {
		/* Skip one message; this should not be re-raised on host */
		/* TODO: this is not optimal check if we can get rid of it */
		tracer->skip_raised_in_event++; 
		controller_Component_Actuator_raise_trigger(tracer->machine);
		return 0;
	}
	if(strcmp(member, "activity") == 0) {
		sc_boolean converted = (msg->value[0] == 't');
		controller_Component_set_activity(tracer->machine, converted);
		return 0;
	}
	if(strcmp(member, "Sensors.triggered") == 0) {
		sc_boolean converted = (msg->value[0] == 't');
		controller_Component_Sensors_set_triggered(tracer->machine, converted);
		return 0;
	}
	if(strcmp(member, "Actuator.triggered") == 0) {
		sc_boolean converted = (msg->value[0] == 't');
		controller_Component_Actuator_set_triggered(tracer->machine, converted);
		return 0;
	}
	
	
	return YET_ERR_INVALID_KEY;
}

