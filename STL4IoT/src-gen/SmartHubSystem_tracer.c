/** Generated by itemis CREATE code generator. */
/* Implements a standard statechart trace handler which adapts to the
 * YET tracing library.
 */


#include "SmartHubSystem_tracer.h"
#include "SmartHubSystem_meta.h"

#include <string.h>
#include <stdlib.h>


#ifndef SC_UNUSED
#define SC_UNUSED(P) (void)(P)
#endif

static sc_string default_scope_name = "SmartHubSystem";

static yet_value_serializer feature_value_serializer[55];



static void init_feature_value_serializer() {
	feature_value_serializer[0] = yet_serialize_void;
	feature_value_serializer[1] = yet_serialize_void;
	feature_value_serializer[2] = yet_serialize_void;
	feature_value_serializer[3] = yet_serialize_void;
	feature_value_serializer[4] = yet_int_serializer_function(sizeof(sc_integer), true);
	feature_value_serializer[5] = yet_serialize_bool;
	feature_value_serializer[6] = yet_serialize_void;
	feature_value_serializer[7] = yet_serialize_void;
	feature_value_serializer[8] = yet_serialize_void;
	feature_value_serializer[9] = yet_serialize_void;
	feature_value_serializer[10] = yet_serialize_void;
	feature_value_serializer[11] = yet_serialize_void;
	feature_value_serializer[12] = yet_serialize_void;
	feature_value_serializer[13] = yet_serialize_void;
	feature_value_serializer[14] = yet_serialize_void;
	feature_value_serializer[15] = yet_serialize_void;
	feature_value_serializer[16] = yet_int_serializer_function(sizeof(sc_integer), true);
	feature_value_serializer[17] = yet_serialize_void;
	feature_value_serializer[18] = yet_serialize_bool;
	feature_value_serializer[19] = yet_serialize_bool;
	feature_value_serializer[20] = yet_serialize_bool;
	feature_value_serializer[21] = yet_serialize_bool;
	feature_value_serializer[22] = yet_serialize_bool;
	feature_value_serializer[23] = yet_int_serializer_function(sizeof(sc_integer), true);
	feature_value_serializer[24] = yet_int_serializer_function(sizeof(sc_integer), true);
	feature_value_serializer[25] = yet_int_serializer_function(sizeof(sc_integer), true);
	feature_value_serializer[26] = yet_serialize_bool;
	feature_value_serializer[27] = yet_serialize_void;
	feature_value_serializer[28] = yet_serialize_void;
	feature_value_serializer[29] = yet_serialize_void;
	feature_value_serializer[30] = yet_serialize_bool;
	feature_value_serializer[31] = yet_int_serializer_function(sizeof(sc_integer), true);
	feature_value_serializer[32] = yet_serialize_void;
	feature_value_serializer[33] = yet_serialize_void;
	feature_value_serializer[34] = yet_serialize_void;
	feature_value_serializer[35] = yet_serialize_bool;
	feature_value_serializer[36] = yet_int_serializer_function(sizeof(sc_integer), true);
	feature_value_serializer[37] = yet_serialize_void;
	feature_value_serializer[38] = yet_serialize_void;
	feature_value_serializer[39] = yet_serialize_void;
	feature_value_serializer[40] = yet_serialize_bool;
	feature_value_serializer[41] = yet_int_serializer_function(sizeof(sc_integer), true);
	feature_value_serializer[42] = yet_serialize_void;
	feature_value_serializer[43] = yet_serialize_void;
	feature_value_serializer[44] = yet_serialize_void;
	feature_value_serializer[45] = yet_serialize_bool;
	feature_value_serializer[46] = yet_int_serializer_function(sizeof(sc_integer), true);
	feature_value_serializer[47] = yet_serialize_void;
	feature_value_serializer[48] = yet_serialize_void;
	feature_value_serializer[49] = yet_serialize_void;
	feature_value_serializer[50] = yet_serialize_void;
	feature_value_serializer[51] = yet_serialize_void;
	feature_value_serializer[52] = yet_serialize_void;
	feature_value_serializer[53] = yet_serialize_void;
	feature_value_serializer[54] = yet_serialize_void;
}

static char* featureName(sc_integer featureId)
{
	return SmartHubSystem_meta_feature_names[featureId];
}

static char* featureValue(sc_integer featureId, const void* valuePtr, char* buf)
{
	return (feature_value_serializer[featureId])(valuePtr, buf);
}

static char* stateName(sc_integer stateId)
{
	return SmartHubSystem_meta_state_names[stateId];
}

static yet_error dispatchMessage(yet_scope *scope, yet_message * msg, char *fqn);

void smartHubSystem_init_sc_tracer(yet_sc_tracer *tracer, SmartHubSystem* machine, sc_string name)
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
	
	
	if(strcmp(member, "toggle") == 0) {
		/* Skip one message; this should not be re-raised on host */
		/* TODO: this is not optimal check if we can get rid of it */
		tracer->skip_raised_in_event++; 
		smartHubSystem_raise_toggle(tracer->machine);
		return 0;
	}
	if(strcmp(member, "HUB.TurnONSystems") == 0) {
		/* Skip one message; this should not be re-raised on host */
		/* TODO: this is not optimal check if we can get rid of it */
		tracer->skip_raised_in_event++; 
		smartHubSystem_HUB_raise_turnONSystems(tracer->machine);
		return 0;
	}
	if(strcmp(member, "HUB.TurnOFFSystems") == 0) {
		/* Skip one message; this should not be re-raised on host */
		/* TODO: this is not optimal check if we can get rid of it */
		tracer->skip_raised_in_event++; 
		smartHubSystem_HUB_raise_turnOFFSystems(tracer->machine);
		return 0;
	}
	if(strcmp(member, "SmartFire.HUB_reset") == 0) {
		/* Skip one message; this should not be re-raised on host */
		/* TODO: this is not optimal check if we can get rid of it */
		tracer->skip_raised_in_event++; 
		smartHubSystem_SmartFire_raise_hUB_reset(tracer->machine);
		return 0;
	}
	if(strcmp(member, "SmartFire.isON") == 0) {
		/* Skip one message; this should not be re-raised on host */
		/* TODO: this is not optimal check if we can get rid of it */
		tracer->skip_raised_in_event++; 
		smartHubSystem_SmartFire_raise_isON(tracer->machine);
		return 0;
	}
	if(strcmp(member, "SmartFire.isOFF") == 0) {
		/* Skip one message; this should not be re-raised on host */
		/* TODO: this is not optimal check if we can get rid of it */
		tracer->skip_raised_in_event++; 
		smartHubSystem_SmartFire_raise_isOFF(tracer->machine);
		return 0;
	}
	if(strcmp(member, "SmartTV.toggle") == 0) {
		/* Skip one message; this should not be re-raised on host */
		/* TODO: this is not optimal check if we can get rid of it */
		tracer->skip_raised_in_event++; 
		smartHubSystem_SmartTV_raise_toggle(tracer->machine);
		return 0;
	}
	if(strcmp(member, "SmartLight.toggle") == 0) {
		/* Skip one message; this should not be re-raised on host */
		/* TODO: this is not optimal check if we can get rid of it */
		tracer->skip_raised_in_event++; 
		smartHubSystem_SmartLight_raise_toggle(tracer->machine);
		return 0;
	}
	if(strcmp(member, "SmartMicrowave.toggle") == 0) {
		/* Skip one message; this should not be re-raised on host */
		/* TODO: this is not optimal check if we can get rid of it */
		tracer->skip_raised_in_event++; 
		smartHubSystem_SmartMicrowave_raise_toggle(tracer->machine);
		return 0;
	}
	if(strcmp(member, "totalSystemsON") == 0) {
		sc_integer converted = (sc_integer)atol(msg->value);
		smartHubSystem_set_totalSystemsON(tracer->machine, converted);
		return 0;
	}
	if(strcmp(member, "allSystemsOn") == 0) {
		sc_boolean converted = (msg->value[0] == 't');
		smartHubSystem_set_allSystemsOn(tracer->machine, converted);
		return 0;
	}
	if(strcmp(member, "HUB.totalDevices") == 0) {
		sc_integer converted = (sc_integer)atol(msg->value);
		smartHubSystem_HUB_set_totalDevices(tracer->machine, converted);
		return 0;
	}
	if(strcmp(member, "HUB.emergency") == 0) {
		sc_boolean converted = (msg->value[0] == 't');
		smartHubSystem_HUB_set_emergency(tracer->machine, converted);
		return 0;
	}
	if(strcmp(member, "Network.SF_connection") == 0) {
		sc_boolean converted = (msg->value[0] == 't');
		smartHubSystem_Network_set_sF_connection(tracer->machine, converted);
		return 0;
	}
	if(strcmp(member, "Network.STV_connection") == 0) {
		sc_boolean converted = (msg->value[0] == 't');
		smartHubSystem_Network_set_sTV_connection(tracer->machine, converted);
		return 0;
	}
	if(strcmp(member, "Network.SL_connection") == 0) {
		sc_boolean converted = (msg->value[0] == 't');
		smartHubSystem_Network_set_sL_connection(tracer->machine, converted);
		return 0;
	}
	if(strcmp(member, "Network.SMW_connection") == 0) {
		sc_boolean converted = (msg->value[0] == 't');
		smartHubSystem_Network_set_sMW_connection(tracer->machine, converted);
		return 0;
	}
	if(strcmp(member, "Power.index") == 0) {
		sc_integer converted = (sc_integer)atol(msg->value);
		smartHubSystem_Power_set_index(tracer->machine, converted);
		return 0;
	}
	if(strcmp(member, "Power.total") == 0) {
		sc_integer converted = (sc_integer)atol(msg->value);
		smartHubSystem_Power_set_total(tracer->machine, converted);
		return 0;
	}
	if(strcmp(member, "Power.threshold") == 0) {
		sc_integer converted = (sc_integer)atol(msg->value);
		smartHubSystem_Power_set_threshold(tracer->machine, converted);
		return 0;
	}
	if(strcmp(member, "Power.thresholdReached") == 0) {
		sc_boolean converted = (msg->value[0] == 't');
		smartHubSystem_Power_set_thresholdReached(tracer->machine, converted);
		return 0;
	}
	if(strcmp(member, "SmartFire.alarm_isOn") == 0) {
		sc_boolean converted = (msg->value[0] == 't');
		smartHubSystem_SmartFire_set_alarm_isOn(tracer->machine, converted);
		return 0;
	}
	if(strcmp(member, "SmartFire.kWh") == 0) {
		sc_integer converted = (sc_integer)atol(msg->value);
		smartHubSystem_SmartFire_set_kWh(tracer->machine, converted);
		return 0;
	}
	if(strcmp(member, "SmartTV.on") == 0) {
		sc_boolean converted = (msg->value[0] == 't');
		smartHubSystem_SmartTV_set_on(tracer->machine, converted);
		return 0;
	}
	if(strcmp(member, "SmartTV.kWh") == 0) {
		sc_integer converted = (sc_integer)atol(msg->value);
		smartHubSystem_SmartTV_set_kWh(tracer->machine, converted);
		return 0;
	}
	if(strcmp(member, "SmartLight.on") == 0) {
		sc_boolean converted = (msg->value[0] == 't');
		smartHubSystem_SmartLight_set_on(tracer->machine, converted);
		return 0;
	}
	if(strcmp(member, "SmartLight.kWh") == 0) {
		sc_integer converted = (sc_integer)atol(msg->value);
		smartHubSystem_SmartLight_set_kWh(tracer->machine, converted);
		return 0;
	}
	if(strcmp(member, "SmartMicrowave.on") == 0) {
		sc_boolean converted = (msg->value[0] == 't');
		smartHubSystem_SmartMicrowave_set_on(tracer->machine, converted);
		return 0;
	}
	if(strcmp(member, "SmartMicrowave.kWh") == 0) {
		sc_integer converted = (sc_integer)atol(msg->value);
		smartHubSystem_SmartMicrowave_set_kWh(tracer->machine, converted);
		return 0;
	}
	
	
	return YET_ERR_INVALID_KEY;
}

