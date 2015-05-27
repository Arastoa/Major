#include "grammar.h"

multimap<string, string> grammars;
vector<string> vgrammar;
vector<string> vsimbol;
set<string> terset;

void insert_grammar(int n){

	cout << "Insert grammars ( ex)S -> abc , S -> Abc ....)  " << endl;
	for (int i = 0; i < n; i++){
		string simbol;
		string grm;

		cin >> simbol;
		cin.ignore(2, '-');
		cin.ignore(2, '>');
		cin >> grm;

		grammars.insert(make_pair(simbol, grm));
	}
}

//확인하기 원하는 문법 grm에 저장
void set_vgrm(const string& simbol){
	vgrammar.clear();
	pair<multimap<string, string>::iterator, multimap<string, string>::iterator > iter_pair;
	iter_pair = grammars.equal_range(simbol);	//키값으로 영역을 잡아주고 반복자 돌림
	multimap<string, string>::iterator iter;
	for (iter = iter_pair.first; iter != iter_pair.second; iter++)
		vgrammar.push_back(iter->second);
	sort(vgrammar.begin(), vgrammar.end());
}

//set으로 저장된 문법 반환
vector<string> get_vgrm(void){
	return vgrammar;
}

//저장된 모든 문법 출력
void print_grammars(){
	cout << "grammars : " << endl;
	for (multimap<string, string>::iterator itr = grammars.begin(); itr != grammars.end(); itr++)
		cout << itr->first << " -> " << itr->second << endl;
}

void print_sim(){
	for (int i = 0; i < vsimbol.size(); i++)
		cout << vsimbol[i] << (i + 1 == vsimbol.size() ? "\n" : " , ");
	cout << endl;
}

vector<string> get_sim(){
	return vsimbol;
}

//grm에 특정 문법만 저장되어있는 것들 출력
void print_vgrm(){
	vector<string>::iterator itr = vgrammar.begin();
	while (itr != vgrammar.end())
		cout << *(itr++) << ((itr + 1) == vgrammar.end() ? "\n" : " , ");
	cout << endl;
}

void print_terset(){
	cout << "terminal set : " << endl;
	set<string>::iterator itr = terset.begin();
	while (itr != terset.end()){
		cout << *(itr++) << ' ';
	}
	cout << endl << endl;

}

//터미널인지 넌터미널인지 확인
bool is_ter(char ch){
	if (ch >= 'A' && ch <= 'Z')
		return true;
	return false;
}

void set_terset(){
	for (multimap<string, string>::iterator itr = grammars.begin(); itr != grammars.end(); itr++)
		terset.insert(itr->first);
}
