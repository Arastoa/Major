#include "follow.h"

multimap<string, string> follows;
set<string> vfollow;

void set_follows(){
	//원치 않은 원본 침해 막기 위해 grammars와 같은 멀티맵 생성
	multimap<string, string> tmpgrammars(grammars.begin(), grammars.end());
	set_terset();
	follows.insert(make_pair("S", "$"));

	for (set<string>::iterator setiter = terset.begin(); setiter != terset.end(); setiter++){
		set_vgrm(*setiter);
		for (int i = 0; i < vgrammar.size(); i++){
			for (int j = 0; j < vgrammar[i].size(); j++){
				if (is_ter(vgrammar[i].at(j))){
					find_follow(string(1, vgrammar[i].at(j)), vgrammar[i], j + 1);
				}
			}
		}
	}
}

void find_follow(const string& simbol, const string& grm, int index){
	
	if (index == grm.size()){
		follows.insert(make_pair(simbol, "$"));
		return;
	}
	
	if (is_ter(grm[index])){
		set_vfirst(simbol);
		
		for (int i = 0; i < vfirst.size(); i++)
			follows.insert(make_pair(simbol, vfirst[i]));

		if (find(vfirst.begin(), vfirst.end(), "ε") != vfirst.end())
			find_follow(string(1,grm[index]), grm, index + 1);
		return;
	}

	follows.insert(make_pair(simbol, string(1,grm[index])));

	return;
}

void print_follows(){
	cout << "follows : " << endl;
	for (multimap<string, string>::iterator itr = follows.begin(); itr != follows.end(); itr++)
		cout << "FOLLOW(" << itr->first << ") -> " << itr->second << endl;
}
void set_vfollow(string simbol){
	vfollow.clear();
	pair<multimap<string, string>::iterator, multimap<string, string>::iterator > iter_pair;
	iter_pair = follows.equal_range(simbol);	//키값으로 영역을 잡아주고 반복자 돌림
	multimap<string, string>::iterator iter;
	for (iter = iter_pair.first; iter != iter_pair.second; iter++)
		vfollow.insert(iter->second);

}
bool chk_follow(const string& simbol, const string& target){
	return find(vfollow.begin(), vfollow.end(), target) == vfollow.end();
}
