#ifndef MAIN
#define MAIN

#include "first.h"
#include "follow.h"
#include "grammar.h"
#include "LLTable.h"

int main(){
	int n;

	cout << "Input numbers of grammar : ";
	cin >> n;
	
	insert_grammar(n);
	
	//저장된 문법 출력
	print_grammars();

	//만들어진 first 출력
	make_firsts();
	print_firsts();

	//만들어진 follow 출력
	set_follows();
	print_follows();

	//LL table 출력
	print_LLTable();
	
	return 0;
}

#endif
