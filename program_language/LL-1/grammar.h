#ifndef GRAMMAR_H
#define GRAMMAR_H

#include "common_header.h"

using namespace std;

/*

처음엔
static vector<pair<string , vector<string> > grammars;
로 입력받는 문법에 대한 키와 밸류 값을 유지하려했음. 그러나 이런 pair 타입에 대해서 == 오버로딩이 지원되지 않아
구현상의 어려움으로 약간 공간의 낭비는 있지만 multimap으로 우회함

*/

extern multimap<string, string> grammars;
extern vector<string> vgrammar;
extern set<string> terset;

bool is_ter(char ch);
void insert_grammar(int n);
void set_vgrm(const string& simbol);
bool decision(const pair<string, string>& p1, const pair<string, string>& p2);
void print_grammars();
void print_vgrm();
void print_sim();
void print_terset();
void set_terset();
vector<string> get_sim();
vector<string> get_vgrm(void);

#endif
