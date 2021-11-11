#include "smoker.h"

void* smoke(void* param){
	TY_SMOKER pSmoker = *((PTY_SMOKER) param);
	
	while(!end){
		smokerSmokes(pSmoker.id, pSmoker.pTable);	
	}
}
