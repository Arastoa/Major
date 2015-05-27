#ifndef LLTABLE
#define LLTABLE

#include "common_header.h"
#include "grammar.h"
#include "first.h"

using namespace std;

void print_LLTable();
void vgrm_ntsetting(vector<string>& ntvgrm);
void sort_ntvgrm(vector<string>& ntvgrm);
int operator <(const string& str1, const string& str2);
int sort_decision(string str1, string str2);

#endif
