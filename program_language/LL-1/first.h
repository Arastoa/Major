#ifndef FIRST
#define FIRST

#include "common_header.h"
#include "grammar.h"

using namespace std;


//아스키 코드표에 입실론이 없어서 밸류도 string으로 줌.
extern multimap<string, string> firsts;
extern vector<string> vfirst;
extern set<string> nontset;

void find_first(const string& simbol, const string& target, int index);
bool chk_first(const string& simbol, const string& target);
void set_vfirst(const string& simbol);
void make_firsts();
void print_firsts();
void set_nontset();
void print_nontset();


#endif
