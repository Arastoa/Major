#ifndef FOLLOW
#define FOLLOW

#include "common_header.h"
#include "grammar.h"
#include "first.h"

using namespace std;

extern multimap<string, string> follows;
extern set<string> vfollow;

void set_follows();
void set_vfollow(string simbol);
void print_follows();
bool chk_follow(const string& simbol, const string& target);
void find_follow(const string& simbol, const string& grm, int index);

#endif
