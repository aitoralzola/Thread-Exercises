#include "agent.h"

void* put(void* param){
	PTY_AGENT pAgent = (PTY_AGENT) param;
	
	while(!end){
		putIngredients(pAgent->pTable);	
	}
}
