#include "first.h"

multimap<string, string> firsts;
vector<string> vfirst;
set<string> nontset;

void make_firsts(){
	//원치 않은 원본 침해 막기 위해 grammars와 같은 멀티맵 생성
	multimap<string, string> tmpgrammars(grammars.begin(), grammars.end());
	
	for (int i = 0; i < 2; i++){
		if (!i){	//첫 큰 반복문에서는 넌터미널에 대해서만 first만들어줌
			for (multimap<string, string>::iterator itr = tmpgrammars.begin(); itr != tmpgrammars.end(); itr++){
				set_vfirst(itr->first);
				if ( (itr->second).compare("ε") == 0) //입실론일경우
					firsts.insert(make_pair(itr->first, "ε"));
				else if (!is_ter(itr->second[0])){
					if (chk_first(itr->first, string(1,itr->second[0]))){
							firsts.insert(make_pair(itr->first, string(1, itr->second[0])));
					}
				}
			}
		}
		else{	//두번째 큰 반복문에서는 터미널이 맨 앞에 나온 문장이 있을 경우 위에서 만들어진 first(터미널)을 보고 넌터미널 넣어줌
			for (multimap<string, string>::iterator itr = tmpgrammars.begin(); itr != tmpgrammars.end(); itr++){
				if (is_ter(itr->second[0])){
					string sim(1, itr->second[0]);
					set_vfirst(sim);
					vector<string> tmp (vfirst.begin(),vfirst.end());

					//입실론 처리해줘야함
					//bool next_decision = find(tmp.begin(), tmp.end(), "ε") != tmp.end();
					//좌측순환문법이 나올 경우 어떻게 처리해줘야될지 감이 안잡혀서 구현 못함
					
					set_vfirst(itr->first);
					for (int i = 0; i < tmp.size(); i++){
						if (chk_first(itr->first, tmp[i]))
							firsts.insert(make_pair(itr->first, tmp[i]));
					}
				}
					
			}
		}
	}
}
void set_vfirst(const string& simbol){
	vfirst.clear();
	pair<multimap<string, string>::iterator, multimap<string, string>::iterator > iter_pair;
	iter_pair = firsts.equal_range(simbol);	//키값으로 영역을 잡아주고 반복자 돌림
	multimap<string, string>::iterator iter;
	for (iter = iter_pair.first; iter != iter_pair.second; iter++)
		vfirst.push_back(iter->second);
}
bool chk_first(const string& simbol, const string& target){
	
	if (find(vfirst.begin(), vfirst.end(), target) != vfirst.end()){
		cout << "This is Not LL(1)" << endl;
		exit(1);
	}
	return true;
}

void print_firsts(){
	cout << "firsts : " << endl;
	for (multimap<string, string>::iterator itr = firsts.begin(); itr != firsts.end(); itr++)
		cout << "FIRST("<<itr->first << ") -> " << itr->second << endl;
}

void set_nontset(){
	for (multimap<string, string>::iterator itr = firsts.begin(); itr != firsts.end(); itr++)
		nontset.insert(itr->second);
}

void print_nontset(){
	cout << "nonterminal set : " << endl;
	set<string>::iterator itr = nontset.begin();
	while (itr != nontset.end()){
		cout << *(itr++) << ' ';
	}
	cout << endl << endl;
}
