use strict;
use warnings;
use utf8;
use Getopt::Long;

sub checkIfParenthesesAreBalanced {
	#$_[0] $syntaxTree
	my @syntaxTreeArray = split(//, $_[0]);
	my $parenthesesCount = 0;
	my $initialLeftParenthesisFlag = 0;
	
	for my $char (@syntaxTreeArray) {
		if($parenthesesCount < 0) {
			die("The parentheses are not balanced.\n");
		}
		if($char =~ /$left/) {
			$parenthesesCount++;
			$initialLeftParenthesisFlag = 1;
		}
		elsif ($char =~ /$right/) {
			$parenthesesCount--;
		}
	}	
	if($initialLeftParenthesisFlag = 0 || $parenthesesCount != 0) {
		die("The parentheses are not balanced or there are no parentheses in this string.\n");
	}
	
}

sub findGornAddress {
	
	my @address = @{$_[0]};
	my $sizeOfAddress = @address;
	my $subTree = $_[1];
	my $parenthesesCounter = 0;
	my $setCounter = 0;
	my $firstParenthesisTag = 0;
	
	my $result = "";
	my $testing = "";
	
	foreach my $char (split //, $_[1]) {
		if($setCounter == $address[0]) {
			if($sizeOfAddress == 1) {	
				$parenthesesCounter = 0;
				$firstParenthesisTag = 0;
				foreach $char (split //, $subTree) {
					if($char =~ /$left/) {
						$parenthesesCounter++;
					}
					elsif($char =~ /$right/) {
						$parenthesesCounter--;
					}
					if($parenthesesCounter > 0 && $firstParenthesisTag == 1) {
						$result .= $char;
					}
					if($char =~ /$left/) {
						$firstParenthesisTag = 1;
					}
					if($firstParenthesisTag == 1 && $parenthesesCounter <= 0) {
						return $result;
					}
				}
			} else {
				if($subTree =~ /$left(.*)/) {
					$subTree = $1;
				}
				@address = @address[1..$sizeOfAddress-1];
				my $result = findGornAddress(\@address, $subTree);
				return $result;
			}
		}
		
		$subTree = substr $subTree, 1;
		
		if ($char =~ /$left/) {
			$parenthesesCounter++;
			$firstParenthesisTag++;
		} elsif ($char =~ /$right/) {
			$parenthesesCounter--;
			if($parenthesesCounter == 0) {
				$setCounter++;
			}
		}
	}
}

my $syntaxTree = "";
my $gornAddress = "";
my $file = "";
my $left = "(";
my $right = ")";
my $help = 0;

GetOptions('syntaxTree=s' => \$syntaxTree, 'gornAddress=s' => \$gornAddress, 'file=s' => \$file, 'left=s' => \$left, 'right=s' => \$right, 'help' => \$help);

$left = quotemeta($left);
$right = quotemeta($right);

if ($help) {
	print "usage: -syntaxTree -gornAddress [-left] [-right] [-help]
\nFind the section of the syntax tree that corresponds to the provided Gorn Address
\nrequired arguments:
 -syntaxTree\ta syntax tree
 -gornAddress\ta gorn address corresponding to the syntax tree
\noptional arguments:
 -left\t\tchange the default left bracket
 -right\t\tchange the default right bracket
 -help\t\tshow this help message and exit\n";
	die;
} 

checkIfParenthesesAreBalanced($syntaxTree);

my @address = split(/\./, $gornAddress);
my $test = findGornAddress(\@address, $syntaxTree);
print $test . "\n";

